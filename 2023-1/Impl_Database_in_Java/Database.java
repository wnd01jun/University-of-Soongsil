package database;
import java.io.*;
import java.util.*;
import java.util.List;
public class Database {
    // 테이블명이 같으면 같은 테이블로 간주된다.
    private static final Set<Table> tables = new HashSet<>();

    // 테이블 이름 목록을 출력한다.
    public static void showTables() {

    }

    /**
     * 파일로부터 테이블을 생성하고 table에 추가한다.
     *
     * @param csv 확장자는 csv로 가정한다.
     *            파일명이 테이블명이 된다.
     *            csv 파일의 1행은 컬럼명으로 사용한다.
     *            csv 파일의 컬럼명은 중복되지 않는다고 가정한다.
     *            컬럼의 데이터 타입은 int 아니면 String으로 판정한다.
     *            String 타입의 데이터는 ("), ('), (,)는 포함하지 않는 것으로 가정한다.
     */
    public static void createTable(File csv) throws FileNotFoundException {
        BufferedReader br = null;
        String inputData = "";
        int checkFirst = -1;




        List<Column> headCreater = new ArrayList<>();
        try{
            br = new BufferedReader(new FileReader(csv));
            while((inputData = br.readLine()) != null){
                String[] getHeadLine = inputData.split(",");
                if(checkFirst == -1){


                    for(String it : getHeadLine){
                        headCreater.add(new ColumnImpl(it));
                    }
                }
                else{
                    for(int i = 0; i < getHeadLine.length; i++){
                        headCreater.get(i).setValue(checkFirst, getHeadLine[i]);
                    }
                    if(headCreater.size() > getHeadLine.length){
                        for(int i = getHeadLine.length; i < headCreater.size(); i++){
                            headCreater.get(i).setValue(checkFirst, null);
                        }
                    }
                }


                checkFirst++;



            }


            String tmp = csv.getName().substring(0, csv.getName().length() - 4);

            tables.add(new TableImpl(tmp, headCreater));
        }catch(FileNotFoundException e){}
        catch(IOException e){}
    }

    // tableName과 테이블명이 같은 테이블을 리턴한다. 없으면 null 리턴.
    public static Table getTable(String tableName) {
        for(Table it : tables){
            if(tableName.equals(it.getName())) return it;
        }
        return null;
    }

    /**
     * @return 정렬된 새로운 Table 객체를 반환한다. 즉, 첫 번째 매개변수 Table은 변경되지 않는다.
     * @param byIndexOfColumn 정렬 기준 컬럼, 존재하지 않는 컬럼 인덱스 전달시 예외 발생시켜도 됨.
     */
    public static Table sort(Table table, int byIndexOfColumn, boolean isAscending, boolean isNullFirst) {
        List<Integer> checker = new ArrayList<>();
        List<Integer> checker2 = new ArrayList<>();

        List<Integer> nullChecker = new ArrayList<>();
        Column tmp = table.getColumn(byIndexOfColumn);
        List<String> tmpString = new ArrayList<>();
        List<String> tmpString2 = new ArrayList<>();

        List<Column> newTable = new ArrayList<>();

        for(int i = 0; i < table.getColumnCount(); i++){
            newTable.add(new ColumnImpl(table.getColumn(i).getHeader()));
        }
        int count = tmp.count();
        for(int i = 0; i < count; i++){
            if(tmp.getValue(i) == null){
                nullChecker.add(i);
            }
            else {
                checker.add(i);
                tmpString.add(tmp.getValue(i));
                tmpString2.add(tmp.getValue(i));
            }
        }
        Collections.sort(tmpString);
        for(String it : tmpString){
            for(int i = 0; i < tmpString.size(); i++){
                if(it.equals(tmpString2.get(i))){
                    checker2.add(checker.get(i));
                    break;
                }

            }
        }
        int k = 0, j = 0;
        if(isNullFirst){
            for(int it : nullChecker){
                j = 0;
                for(int i = 0; i < table.getColumnCount(); i++){
                    newTable.get(i).setValue(k, table.getColumn(i).getValue(it));
                }
                k++;
            }
        }
        if(!isAscending){
            Collections.reverse(checker2);
        }
        j = 0; k = newTable.get(0).count();
        for(int it : checker2){
            j = 0;
            for(int i = 0; i < table.getColumnCount(); i++){
                newTable.get(i).setValue(k, table.getColumn(i).getValue(it));
            }
            k++;
        }
        j = 0; k = newTable.get(0).count();
        if(!isNullFirst){
            for(int it : nullChecker){
                j = 0;
                for(int i = 0; i < table.getColumnCount(); i++){
                    newTable.get(i).setValue(k, table.getColumn(i).getValue(it));
                }
                k++;
            }
        }
        Table tmpTable = new TableImpl(table.getName()+"_sort", newTable);

        return tmpTable;


    }
}
