#include <errno.h>
#include <fcntl.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define BUFSIZE 1024
#define EOF -1
#define stdin 1
#define stdout 2
#define stderr 3
#define SEEK_SET 0
#define SEEK_CUR 1
#define SEEK_END 2
#define CAN_READ 4
#define CAN_WRITE 2
#define THIS_APPEND 1

/*
    buffer는 BUFSIZE 크기의 블럭으로 페이징 하며 관리한다.
    BUFSIZE 1024이므로 0 : 0 ~ 1023, 1 : 1024 ~ 2047 ...
*/

/*
    read : seek 위치가 버퍼보다 +1페이지 앞서있음 (읽기 용이하게 하기 위해) ->
   처음 읽은값이 버퍼보다 작은경우는?
    -> 이 경우에 file을 open 했을 때, seek 위치가 paging 되지 않음 -> read시에는
   문제 x write시 조정 필요 write : seek 위치가 버퍼의 위치와 동일함 (쓸 때
   용이하게 하기 위해) read -> write : flush 불 필요 but write 특성상 seek 위치
   바꿔줘야됨 write ->
*/

typedef struct myFile {
  int fd;
  int pos; // page에서 몇번째 pos인지
  int size;
  int mode; // 0 r 0 w 0 x
  int flag;
  int offset; // buffer size기반 buffer paging위해 사용
  char lastop;
  bool eof;
  char *buffer;
} FILE;
// 파일의 현재 위치 판별 = offset * BUFSIZ + pos
// offset * BUFSIZ + pos > size 인 경우 eof!!

int fflush(FILE *stream);
int getCurrent(FILE *stream);
int fseek(FILE *stream, int offset, int whence);

void eof_check(FILE *stream);
FILE *file_open(int mode, bool eof, int flag, const char *pathname);

FILE *fopen(const char *pathname, const char *mode) {
  FILE *f;
  int total = 0;
  char tmp[BUFSIZE];
  int cnt = 0;
  int fd;
  if (strcmp(mode, "r") == 0) {
    f = file_open(4, false, O_RDONLY, pathname);
  } else if (strcmp(mode, "r+") == 0) {
    f = file_open(6, false, O_RDWR, pathname);
  } else if (strcmp(mode, "w") == 0) {
    f = file_open(2, true, O_WRONLY | O_CREAT | O_TRUNC, pathname);
  } else if (strcmp(mode, "w+") == 0) {
    f = file_open(6, true, O_RDWR | O_CREAT | O_TRUNC, pathname);
  } else if (strcmp(mode, "a") == 0) {
    f = file_open(3, true, O_WRONLY | O_CREAT | O_APPEND, pathname);
  } else if (strcmp(mode, "a+") == 0) {
    f = file_open(7, true, O_RDWR | O_CREAT | O_APPEND, pathname);
  } else {
    return NULL;
  }

  return f;
}

int fread(void *ptr, int size, int nmemb, FILE *stream) {
  if ((stream->mode & CAN_READ) == 0) {
    return -1; // 읽기모드가 포함되어있지 않으면 읽을 수 없다.
  }

  int length = size * nmemb;
  int k = 0;
  if (stream->lastop == 'w') {
    //마지막 연산이 write였을 경우, fflush로 file 최신화
    fflush(stream);
    read(stream->fd, stream->buffer, BUFSIZE);
  }
  stream->lastop = 'r';
  int start = stream->pos + stream->offset * BUFSIZE; // 읽기 시작한 부분
  if (start + length >=
      stream->size) { // 읽으려는 크기가 size보다 큰 경우 size에 맞게 줄인다.
    length = stream->size - start;
    stream->eof = true;
    // write(stdout, "check3\n", 7);
  }

  if (start + length <= stream->size &&
      stream->pos + length < BUFSIZE) { // 읽으려고하는게 버퍼 안에 있는경우
    memcpy(ptr, stream->buffer + stream->pos, length);
    stream->pos += length;
    // if (stream->pos == BUFSIZE) {
    //   stream->pos = 0;
    //   stream->offset++;
    //   if (read(stream->fd, stream->buffer, BUFSIZE) == -1) {
    //     stream->eof = true;
    //   }
    // }
    return length;
  }

  int result = length;
  k = BUFSIZE - stream->pos; // 처음에 버퍼 내에서 읽는 양
  memcpy(ptr, stream->buffer + stream->pos, k);
  length -= k;
  stream->offset++;
  stream->pos = 0;
  // while(length >= BUFSIZE){ // BUFSIZE만큼 파일로부터 BUFSIZE보다 작아질 때
  // 까지 읽는다
  //     read(stream -> fd, ptr + k, BUFSIZE);
  //     length -= BUFSIZE;
  //     k += BUFSIZE;
  //     stream -> offset++;
  // }
  int g = length / BUFSIZE;
  if (g) {
    read(stream->fd, ptr + k, BUFSIZE * g);
    length -= BUFSIZE * g;
    k += BUFSIZE * g;
    stream->offset += g;
  }
  read(stream->fd, stream->buffer, BUFSIZE);
  if (length) { // 아직 읽을 크기가 남아있는 경우
    memcpy(ptr + k, stream->buffer, length);
    stream->pos = length;
  }
  return result;

  /*이전버전

  if(stream -> pos + length <= stream -> size){
      memcpy(ptr, stream -> buffer + stream -> pos, stream -> pos + length + 1);
      stream -> pos += length + 1;
      k = length;
  }
  else{
      memcpy(ptr, stream -> buffer + stream -> pos, stream -> size + 1);
      stream -> pos = stream -> size + 1;
      k = stream -> size - stream -> pos + 1;
  }
  stream -> lastop = 'r';
  eof_check(stream);
  return k;
  */
}

int fwrite(const void *ptr, int size, int nmemb, FILE *stream) {

  if ((stream->mode & CAN_WRITE) == 0) {
    return -1; // 쓰기모드가 아니면 write를 할 수 없다.
  }
  if (stream->mode & THIS_APPEND) { // append 모드를 위한 따로 처리 쓰기버퍼와
                                    // 읽기버퍼 완전 분리
    int length = size * nmemb; // 쓰기버퍼는 pos로만 관리한다
    stream->size += length;
    if (stream->lastop != 'w') {
      free(stream->buffer);
      stream->buffer = (char *)malloc(sizeof(char) * BUFSIZE);
      stream->pos = 0;
      stream->eof = true;
      stream->lastop = 'w';
    }
    if (stream->pos + length >= BUFSIZE) {
      int g = BUFSIZE - stream->pos;
      memcpy(stream->buffer + stream->pos, ptr, g);
      write(stream->fd, stream->buffer, BUFSIZE);
      length -= g;
      int k = length / BUFSIZE;
      write(stream->fd, ptr + g, BUFSIZE * k);
      length -= BUFSIZE * k;
      stream->pos = length;
      memcpy(stream->buffer, ptr + g + BUFSIZE * k, length);

    } else {
      memcpy(stream->buffer + stream->pos, ptr, length);
      stream -> pos = length;
    }
    return size * nmemb;
  }

  
  if (stream->lastop == 'r') {
    lseek(stream->fd, stream->offset * BUFSIZE, SEEK_SET);
  }

  stream->lastop = 'w';

  int k = 0;
  int length = size * nmemb;
  if (stream->pos + length < BUFSIZE) {
    memcpy(stream->buffer + stream->pos, ptr,
           length + 1); // 버퍼에 공간이 충분하므로 버퍼에만 작성한다.
    stream->pos += length;
    if (stream->pos + stream->offset * BUFSIZE > stream->size) {
      stream->size = stream->pos + stream->offset * BUFSIZE; // 사이즈 갱신
    }
    return length;
  } else {
    int tmp = BUFSIZE - stream->pos;
    int start = BUFSIZE * stream->offset + stream->pos;
    memcpy(stream->buffer + stream->pos, ptr,
           tmp); // 버퍼 남아있는 공간만큼은 버퍼에쓰고 이를 파일에 작성한다.
    lseek(stream->fd, stream->offset * BUFSIZE, SEEK_SET);
    write(stream->fd, stream->buffer, BUFSIZE);
    length -= tmp;
    stream->offset++;
    // while(length >= BUFSIZE){ // 남아있는 length의 크기가 BUFSIZE 미만으로
    // 내려갈 때 까지
    //     write(stream -> fd, ptr + tmp, BUFSIZE); // 버퍼에 넣을 수 없으므로
    //     바로 파일에 작성한다. length -= BUFSIZE; tmp += BUFSIZE; stream ->
    //     offset++;
    // }

    int g = length / BUFSIZE;
    if (g) {
      write(stream->fd, ptr + tmp, BUFSIZE * g);
      length -= BUFSIZE * g;
      tmp += BUFSIZE * g;
      stream->offset += g;
    }

    // 아직 써야할 문자열이 남아있다면
    if (start + length + tmp >
        size) { // 총 크기가 기존 파일의 사이즈보다 커지면
      if (length)
        memcpy(stream->buffer, ptr + tmp, length);
      stream->pos = length;
      stream->size = stream->offset * BUFSIZE +
                     length; // 버퍼를 불러오지않고 사이즈 갱신하고 버퍼에 작성
      stream->eof = true;
      tmp += length;
    } else {
      int k = read(stream->fd, stream->buffer, BUFSIZE); // 버퍼를 불러오고
      if (length)
        memcpy(stream->buffer, ptr + tmp, length); // 남은 길이만큼 파일에 작성
      stream->pos = length;
      tmp += length;
    }
    char ggg[1] = {'0'};
    ggg[0] += tmp;
    write(stdout, ggg, 1);
    return tmp;
  }

  /* --------- 이전 버전

      if(stream -> pos + length < stream -> size){
          memcpy(stream -> buffer + stream -> pos, (char *) ptr, length + 1);
          // lseek(stream -> fd, stream -> pos, SEEK_SET);
          if((write(stream -> fd, stream -> buffer, length)) != length){
              //예외처리
          }
          stream -> pos += length;
          k = length;
      }
      else{
          char *tmp = (char *)malloc(sizeof(char) * (stream -> pos + length));
          memcpy(tmp, stream -> buffer, stream -> pos);
          free(stream -> buffer);
          stream -> buffer = tmp;
          memcpy(stream -> buffer + stream -> pos, ptr, length);


          write(stdout, stream -> buffer, length);
          // lseek(stream -> fd, stream -> pos, SEEK_SET);
          if((write(stream -> fd, stream -> buffer, length)) != length){
              //예외처리
          }
          stream -> pos += length + 1;
          k = length;
      }
      return k;*/
}

int fseek(FILE *stream, int offset, int whence) {
  if (stream->lastop == 'w') {
    fflush(stream);
  }
  int siz = 0;

  if (whence == SEEK_SET) {
    siz = 0;
  } else if (whence == SEEK_CUR) {
    siz = getCurrent(stream);
  } else if (whence == SEEK_END) {
    siz = stream->size;
  }
  int l = (siz / BUFSIZE) * BUFSIZE;
  int r = l + BUFSIZE;
  siz += offset;
  if (siz < 0 || siz > stream->size)
    return -1; // 0이하거나 size보다 커지는 경우 이동불가로 지정

  if (siz >= l && r >= siz) {
    stream->pos = siz - l;
    if(stream -> mode & THIS_APPEND){
      lseek(stream->fd, l, SEEK_SET);
      int k = read(stream->fd, stream->buffer, BUFSIZE);
    }
  } else {
    lseek(stream->fd, l, SEEK_SET);
    stream->pos = siz - l;
    int k = read(stream->fd, stream->buffer, BUFSIZE);
  }
  if (siz == stream->size)
    stream->eof = true;
  else
    stream->eof = false;
  stream->lastop = 's';
  return 0;
}

int fflush(FILE *stream) {
  int k = 0;
  if (stream->lastop == 'w') {
    k = write(stream->fd, stream->buffer, stream->pos);
    // 버퍼를 비우며 사용하는게 아니라 pos 위치만 바꾸며 사용하기 때문에
    // BUFSIZE로 할경우 예상치 못한 값이 들어올 수 있음 so pos까지
    if ((stream->mode & THIS_APPEND) == 0)
      lseek(stream->fd, stream->offset * BUFSIZE, SEEK_SET);
    else {
      stream->offset = stream->size / BUFSIZE;
      stream->pos = stream->size % BUFSIZE;
      lseek(stream->fd, stream->offset * BUFSIZE, SEEK_SET);
    }
  }
  if (k >= 1) {
    stream->lastop = 'l';
    return 0;
  } else {
    return EOF;
  }
}

int fclose(FILE *stream) {
  fflush(stream);
  close(stream->fd);
  free(stream->buffer);
  return 1;
}

int feof(FILE *stream) { return stream->eof ? EOF : 0; }

FILE *file_open(int mode, bool eof, int flag, const char *pathname) {
  FILE *f = (FILE *)malloc(sizeof(FILE));
  f->mode = mode;
  f->flag = flag;
  f->lastop = 'x';
  f->eof = eof;
  f->size = 0;
  if ((f->fd = open(pathname, f->flag, 0644)) <
      0) { // 문제있을시 3번째 파라미터에 0644 넣어라...
    // error
    write(stdout, strerror(errno), 100);
    write(stdout, "--\n", 4);
    return NULL;
  }
  // https://bubble-dev.tistory.com/entry/CC-lseek-%ED%95%A8%EC%88%98-%ED%8C%8C%EC%9D%BC-%EC%BB%A4%EC%84%9Cseek-pointer-%EC%A1%B0%EC%A0%95
  if ((f->size = lseek(f->fd, 0, SEEK_END)) <
      0) { // lseek의 end크기는 파일의 size와 동일
           //예외 발생
  }

  f->buffer = (char *)malloc(sizeof(char) * BUFSIZE); // 기본값 BUFSIZE로 설정
  int txt = 0;

  if (f->eof == false) { // eof == false -> read인 경우
    lseek(f->fd, 0, SEEK_SET);
    if ((f->mode & THIS_APPEND) == 0)
      txt = read(f->fd, f->buffer, BUFSIZE); // BUFSIZE만큼 buffer에 가져온다
    else {
      f->lastop = 'w'; // append 모드는 쓰기모드부터 시작.
    }
    if (txt == -1)
      f->eof = true; // 만약 빈 파일일 경우 eof 활성화!
    f->pos = 0;
    f->offset = 0; // 초기 설정은 가장 첫부분부터
  } else {
    f->offset = (f->size) / BUFSIZE; // paging
    f->pos = (f->size) % BUFSIZE;    // pos 위치
    lseek(f->fd, f->offset * BUFSIZE, SEEK_SET);
    if (!(f->flag & O_TRUNC) &&
        f->pos > 0) { // append일 경우 paging에 맞게 buffer에 넣기
      txt = read(f->fd, f->buffer, BUFSIZE);
      if (txt != -1) {
        f->pos += txt;
      }
    }
  }
  return f;
}

void eof_check(FILE *stream) { stream->eof = (stream->size < stream->pos); }

int getCurrent(FILE *stream) { return stream->pos + stream->offset * BUFSIZE; }