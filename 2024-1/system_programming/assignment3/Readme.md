리눅스 파일 입출력인
fopen, fclose, fseek, fread, fwrite, feof, fflush
구현이다.

리눅스 표준 시스템콜을 이용하여 구현하였으며
버퍼를 통해 버퍼내의 파일 입출력은 시스템콜의 사용을 최소화 하는 방향으로 구현하였다.