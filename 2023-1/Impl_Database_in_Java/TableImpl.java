package database;
import java.sql.Array;
import java.util.*;
import java.util.function.Predicate;

class TableImpl implements Table {
    private List<Column> table;
    private String tableName;
    TableImpl(String tableName, List<Column> inputTable){

        table = inputTable;
        this.tableName = tableName;
    }


    // 테이블의 이름을 반환
    @Override
    public String getName(){
        return tableName;
    }

    // 테이블 헤더 + 내용 출력
//    @Override
//    public void show(){
//        int k = table.get(0).count();
//        for(Column it : table){
//            System.out.printf("%s %-5s", it.getHeader(), "|");
//        }
//
//        System.out.println("");
//        for(int i = 0; i < k; i++){
//            for(Column it : table){
//                System.out.printf("%s %-5s", it.getValue(i), "|");
//            }
//            System.out.println("");
//        }
//    }
    @Override
    public void show(){
        for(int i = 0; i < table.get(0).count() + 1; i++) {
            for (Column it : table) {
                it.show();
            }
            System.out.println();
        }
//        System.out.flush();
    }






    // 테이블 요약 정보 출력
    @Override
    public void describe() {
        int count = 0;
        System.out.println('<'+this.getClass().getName()+'@'+Integer.toHexString(hashCode())+'>');
        System.out.println("RangeIndex: " + table.get(0).count() + " entries, 0 to " + (table.get(0).count() - 1));
        System.out.println("Data Columns (total " + table.size() + " columns):");
        int columnLength = headMax();
        System.out.print("# | ");
        for(int i = "Column".length(); i < columnLength; i++) {
            System.out.print(" ");
        }
        System.out.print("Column | ");
        System.out.printf("%15s | %7s\n", "Non-Null Count", "Dtype");
        int intCount = 0;
        for (Column it : table) {
            if (it.isNumericColumn()) {
                System.out.print((count++) + " | ");
                for (int i = it.getHeader().length(); i < columnLength; i++) {
                    System.out.print(" ");
                }
                System.out.print(it.getHeader() + " | ");
                System.out.printf("%15s | %7s\n", it.count() - it.getNullCount(), "int");
                intCount++;
            }
            else {
                System.out.print((count++)+" | ");
                for(int i = it.getHeader().length(); i < columnLength; i++) {
                    System.out.print(" ");
                }
                System.out.print(it.getHeader()+" | ");
                System.out.printf("%15s | %7s\n", it.count() - it.getNullCount(), "String");
            }
        }
        System.out.println("dtypes: int("+intCount+"), String("+(this.getColumnCount() - intCount)+")");
    }
    private int headMax(){
            int max = "Column".length();
            for(Column it : table){
                if(it.getHeader().length() > max){
                    max = it.getHeader().length();
                }
            }
            return max;
        }



    /**
     * @return 처음 (최대)5개 행으로 구성된 새로운 Table 생성 후 반환
     */
    @Override
    public Table head(){
        return head(5);
    }
    /**
     * @return 처음 (최대)lineCount개 행으로 구성된 새로운 Table 생성 후 반환
     */
    @Override
    public Table head(int lineCount){
        List<Column> tmp = new ArrayList<>();
        for(Column it : table){
            tmp.add(new ColumnImpl(it.getHeader()));
        }
        int howMany = 0;
        if(table.get(0).count() > lineCount) {
            howMany = lineCount;
        }
        else howMany = table.get(0).count();

        int k = 0;
        for(Column it : table){
            for(int i = 0; i < howMany; i++){
                tmp.get(k).setValue(i, it.getValue(i));
            }
            k++;
        }
        return new TableImpl(tableName+"_head", tmp);
    }

    /**
     * @return 마지막 (최대)5개 행으로 구성된 새로운 Table 생성 후 반환
     */
    @Override
    public Table tail(){
        return tail(5);
    }
    /**
     * @return 마지막 (최대)lineCount개 행으로 구성된 새로운 Table 생성 후 반환
     */
    @Override
    public Table tail(int lineCount){
        List<Column> tmp = new ArrayList<>();
        for(Column it : table){
            tmp.add(new ColumnImpl(it.getHeader()));
        }
        int howMany = table.get(0).count() - lineCount;
        int max = table.get(0).count();
        if (howMany < 0) howMany = 0;
        int k = 0;
        int j = 0;
        for(Column it : table){
            j = 0;
            for(int i = howMany; i < max; i++){
                tmp.get(k).setValue(j++, it.getValue(i));
            }
            k++;
        }
        return new TableImpl(tableName+"_tail", tmp);
    }

    /**
     * @param beginIndex 포함(이상)
     * @param endIndex 미포함(미만)
     * @return 검색 범위에 해당하는 행으로 구성된 새로운 Table 생성 후 반환
     * 존재하지 않는 행 인덱스 전달시 예외 발생해도 됨
     */
    @Override
    public Table selectRows(int beginIndex, int endIndex){
        List<Column> tmp = new ArrayList<>();
        for(Column it : table){
            tmp.add(new ColumnImpl(it.getHeader()));
        }
        int k = 0, j = 0;
        for(Column it : table){
            j = 0;
            for(int i = beginIndex; i < endIndex; i++){
                tmp.get(k).setValue(j++, it.getValue(i));
            }
            k++;
        }
        return new TableImpl("Sub_"+tableName, tmp);
    }

    /**
     * @return 검색 인덱스에 해당하는 행으로 구성된 새로운 Table 생성 후 반환
     * 결과 테이블의 행 출현 순서는 매개변수 인덱스 순서와 동일함
     * 존재하지 않는 행 인덱스 전달시 예외 발생해도 됨
     */
    @Override
    public Table selectRowsAt(int ...indices){
        List<Column> tmp = new ArrayList<>();
        for(Column it : table){
            tmp.add(new ColumnImpl(it.getHeader()));
        }
        int k = 0, j = 0;
        for(Column it : table){
            j = 0;
            for(int i : indices){
                tmp.get(k).setValue(j++, it.getValue(i));
            }
            k++;
        }
        return new TableImpl("Sub_"+tableName, tmp);
    }

    /**
     * @param beginIndex 포함(이상)
     * @param endIndex 미포함(미만)
     * @return 검색 범위에 해당하는 열로 구성된 새로운 Table 생성 후 반환
     * 존재하지 않는 열 인덱스 전달시 예외 발생해도 됨
     */
    @Override
    public Table selectColumns(int beginIndex, int endIndex){
        List<Column> tmp = new ArrayList<>();
        int count = table.get(0).count();
        int k = -1;
        for(int i = beginIndex; i < endIndex; i++){
            tmp.add(new ColumnImpl(table.get(i).getHeader()));
            k++;
            for(int j = 0; j < count; j++)
            tmp.get(k).setValue(j, table.get(i).getValue(j));

            }
        return new TableImpl("Sub_"+tableName, tmp);
        }


    /**
     * @return 검색 인덱스에 해당하는 열로 구성된 새로운 Table 생성 후 반환
     * 결과 테이블의 열 출현 순서는 매개변수 인덱스 순서와 동일함
     * 존재하지 않는 열 인덱스 전달시 예외 발생해도 됨*/
    @Override
    public Table selectColumnsAt(int ...indices){
        List<Column> tmp = new ArrayList<>();
        int count = table.get(0).count();
        int k = -1;
        for(int i : indices){
            tmp.add(new ColumnImpl(table.get(i).getHeader()));
            k++;
            for(int j = 0; j < count; j++)
                tmp.get(k).setValue(j, table.get(i).getValue(j));

        }
        return new TableImpl("Sub_"+tableName, tmp);
    }

    /**
     * @param
     * @return 검색 조건에 해당하는 행으로 구성된 새로운 Table 생성 후 반환, 제일 나중에 구현 시도하세요.
     */@Override
    public <T> Table selectRowsBy(String columnName, Predicate<T> predicate){
         Column searchingColumn = this.getColumn(columnName);
         List<Integer> checker = new ArrayList<>();
         for(int i = 0; i < this.getRowCount(); i++){ // 1
//             if(searchingColumn.getValue(i) == null){
//                 if(predicate.test((T) searchingColumn.getValue(i))){
//                     checker.add(i);
//                 }
//             }



            try{
             if(searchingColumn.isNumericColumn()){
                 if(predicate.test((T) Integer.valueOf(searchingColumn.getValue(i)))){
                     checker.add(i);
                     //System.out.println(i+" ");
                 }
             }
             else{
             if(predicate.test((T) searchingColumn.getValue(i))){
                 checker.add(i);
             }}
         }catch(ClassCastException e){
                if(predicate.test((T) searchingColumn.getValue(i))){
                    checker.add(i);
                }
            }
            catch(NumberFormatException e){
                if(predicate.test((T) searchingColumn.getValue(i))){
                    checker.add(i);
            }
         }}
         List<Column> newTable = new ArrayList<>();
         for(Column it : table){
             newTable.add(new ColumnImpl(it.getHeader()));
         }int b, k = 0;
         for(int it : checker){
             b = 0;
             for(Column co : table) {
                 newTable.get(b++).setValue(k, co.getValue(it));
             }
         k++;
         }
         return new TableImpl(tableName+"_selectRowBy", newTable);
    }

    /**
     * @return 원본 Table이 정렬되어 반환된다.
     * @param byIndexOfColumn 정렬 기준 컬럼, 존재하지 않는 컬럼 인덱스 전달시 예외 발생시켜도 됨.
     */
    @Override
    public Table sort(int byIndexOfColumn, boolean isAscending, boolean isNullFirst) {
        int a = 0;
        int count = table.get(0).count();
        List<Integer> checker = new ArrayList<>();
        List<Integer> nullCheck = new ArrayList<>();
        Column tmpColumn = new ColumnImpl("tmp");
        Column checkColumn = new ColumnImpl("check");
        for (int i = 0; i < count; i++) {
            if (table.get(byIndexOfColumn).getValue(i) == null) {
                nullCheck.add(i);
            } else {
                checker.add(i);
                tmpColumn.setValue(a, table.get(byIndexOfColumn).getValue(i));
                checkColumn.setValue(a++, table.get(byIndexOfColumn).getValue(i));
            }
        }
        for (int i = 0; i < tmpColumn.count(); i++) {
            String x = tmpColumn.getValue(i);
            int minCheck = i;
            for (int j = i + 1; j < tmpColumn.count(); j++) {
                if (0 < x.compareTo(tmpColumn.getValue(j))) {
                    x = tmpColumn.getValue(j);
                    minCheck = j;
                }
            }
            tmpColumn.setValue(minCheck, tmpColumn.getValue(i));
            tmpColumn.setValue(i, x);
            int k = checker.get(minCheck);
            checker.set(minCheck, checker.get(i));
            checker.set(i, k);
        }
        List<Column> sortedTable = new ArrayList<>();
        for (Column it : table) {
            sortedTable.add(new ColumnImpl(it.getHeader()));
        }
        if (isNullFirst) {
            int j = 0;
            int k = 0;
            for (int i : nullCheck) {
                j = 0;
                for (Column it : table) {
                    sortedTable.get(j++).setValue(k, it.getValue(i));
                }
                k++;
            }
        }
        if (!isAscending) {
            Collections.reverse(checker);
        }
        int j = 0;
        int k = sortedTable.get(0).count();
        for (int i : checker) {
            //System.out.println(i);
            j = 0;
            for (Column it : table) {
                sortedTable.get(j++).setValue(k, it.getValue(i));
            }
            k++;
        } k = sortedTable.get(0).count(); j = 0;
        if (isNullFirst == false) {

            for (int i : nullCheck) {
                j = 0;
                for (Column it : table) {
                    sortedTable.get(j++).setValue(k, it.getValue(i));
                }
                k++;
            }

        }
        table = sortedTable;
        return this;
    }
    @Override
    public int getColumnCount(){return table.size();}
    @Override
    public int getRowCount(){return table.get(0).count();}

    /**
     * @return 원본 Column이 반환된다. 따라서, 반환된 Column에 대한 조작은 원본 Table에 영향을 끼친다.
     */
    @Override
    public Column getColumn(int index){

        return table.get(index);
    }
    /**
     * @return 원본 Column이 반환된다. 따라서, 반환된 Column에 대한 조작은 원본 Table에 영향을 끼친다.
     */
    @Override
    public Column getColumn(String name){
        for(Column it : table){
            if (name.equals(it.getHeader())){
                return it;
            }
        }
        return null;
    }
    @Override
    public Table crossJoin(Table rightTable){
        List<Column> crossTable = new ArrayList<>();
        for(Column it : table){
            crossTable.add(new ColumnImpl(tableName+"."+it.getHeader()));
        }
        for(int i = 0; i < rightTable.getColumnCount(); i++){
            crossTable.add(new ColumnImpl(rightTable.getName()+"_"+rightTable.getColumn(i).getHeader()));
        }
        int originCount = this.getRowCount(); // 2
        int rightRowCount = rightTable.getColumnCount();
        int rightColumnCount = rightTable.getRowCount(); // 3
        int a = 0, b = 0;
        for(int k = 0; k < originCount; k++) {
            for (int i = 0; i < rightColumnCount; i++) {
                a = 0;
                for (Column it : table) {
                    crossTable.get(a++).setValue(b, it.getValue(k));
                }
                b++;
            }
        }
        b = 0;
        for(int k = 0; k < originCount; k++) {
            a = 0;
            for (int j = 0; j < rightTable.getRowCount(); j++) { // 4
                for (int i = 0; i < rightRowCount; i++) {
                    crossTable.get(i + table.size()).setValue(b, rightTable.getColumn(i).getValue(a));
                }
                b++;
                a++;
            }
        }
        System.out.println(crossTable.get(0).count());
        return new TableImpl("crossJoinedTable", crossTable);
    }
    @Override
    public Table innerJoin(Table rightTable, List<JoinColumn> joinColumns) {
        int joinCount = joinColumns.size();
        Map<String, Integer> checker = new HashMap<>();
        List<Integer> thisChecker = new ArrayList<>();
        List<Integer> anotherChecker = new ArrayList<>();
        for (int i = 0; i < joinCount; i++) {
            String option1 = joinColumns.get(i).getColumnOfThisTable();
            String option2 = joinColumns.get(i).getColumnOfAnotherTable();
            Column thisColumn = this.getColumn(option1);
            Column anotherColumn = rightTable.getColumn(option2);
            int thisCount = thisColumn.count();
            int anotherCount = anotherColumn.count();
            for (int a = 0; a < thisCount; a++) {
                for (int b = 0; b < anotherCount; b++) {
                    if(thisColumn.getValue(a) == null || anotherColumn.getValue(b) == null){
                        if(thisColumn.getValue(a) == null){
                            break;
                        }
                        else{
                            continue;
                        }
                    }
                    if (thisColumn.getValue(a).equals(anotherColumn.getValue(b))) {
                        String equalColumn = a + "+" + b;
                        if (checker.get(equalColumn) == null) {
                            checker.put(equalColumn, 1);
                        } else {
                            checker.put(equalColumn, checker.get(equalColumn) + 1);

                        }
                        if (checker.get(equalColumn) == joinCount) {
                            thisChecker.add(a);
                            anotherChecker.add(b);

                        }
                    }
                }
            }
        }
        List<Column> crossTable = new ArrayList<>();
        for (Column it : table) {
            crossTable.add(new ColumnImpl(tableName + "." + it.getHeader()));
        }
        for (int i = 0; i < rightTable.getColumnCount(); i++) {
            crossTable.add(new ColumnImpl(rightTable.getName() + "." + rightTable.getColumn(i).getHeader()));
        }
        int b;
        for (int i = 0; i < thisChecker.size(); i++) {
            b = 0;
            for (Column it : table) {
                crossTable.get(b++).setValue(i,it.getValue(thisChecker.get(i)));
            }
            for(int x = 0; x < rightTable.getColumnCount(); x++){
                crossTable.get(b++).setValue(i,rightTable.getColumn(x).getValue(anotherChecker.get(i)));
            }
        }
        return new TableImpl("crossInnerJoinTable", crossTable);
    }
    @Override
    public Table outerJoin(Table rightTable, List<JoinColumn> joinColumns){
        int joinCount = joinColumns.size();
        Map<String, Integer> checker = new HashMap<>();
        List<Integer> thisChecker = new ArrayList<>();
        List<Integer> anotherChecker = new ArrayList<>();
        for (int i = 0; i < joinCount; i++) {
            String option1 = joinColumns.get(i).getColumnOfThisTable();
            String option2 = joinColumns.get(i).getColumnOfAnotherTable();
            Column thisColumn = this.getColumn(option1);
            Column anotherColumn = rightTable.getColumn(option2);
            int thisCount = thisColumn.count();
            int anotherCount = anotherColumn.count();
            for (int a = 0; a < thisCount; a++) {
                for (int b = 0; b < anotherCount; b++) {
                    if(thisColumn.getValue(a) == null || anotherColumn.getValue(b) == null){
                        if(thisColumn.getValue(a) == null){
                            break;
                        }
                        else{
                            continue;
                        }
                    }
                    if (thisColumn.getValue(a).equals(anotherColumn.getValue(b))) {
                        String equalColumn = a + "+" + b;
                        if (checker.get(equalColumn) == null) {
                            checker.put(equalColumn, 1);
                        } else {
                            checker.put(equalColumn, checker.get(equalColumn) + 1);

                        }
                        if (checker.get(equalColumn) == joinCount) {
                            thisChecker.add(a);
                            anotherChecker.add(b);

                        }
                    }
                }
            }
        }
        int u = 0;
        List<Integer> noMatchingChecker = new ArrayList<>();
        for(int y = 0; y < table.get(0).count() ; y++){
            if(u < thisChecker.size()){
            if(thisChecker.get(u) != y){
                noMatchingChecker.add(y);
            }
            else{
                u++;
            }}
            else noMatchingChecker.add(y);
        }
        List<Column> crossTable = new ArrayList<>();
        for (Column it : table) {
            crossTable.add(new ColumnImpl(tableName + "." + it.getHeader()));
        }
        for (int i = 0; i < rightTable.getColumnCount(); i++) {
            crossTable.add(new ColumnImpl(rightTable.getName() + "." + rightTable.getColumn(i).getHeader()));
        }
        int b;
        for (int i = 0; i < thisChecker.size(); i++) {
            b = 0;
            for (Column it : table) {
                crossTable.get(b++).setValue(i, it.getValue(thisChecker.get(i)));
            }
            for (int x = 0; x < rightTable.getColumnCount(); x++) {
                crossTable.get(b++).setValue(i, rightTable.getColumn(x).getValue(anotherChecker.get(i)));
            }
        }
        int crossColumnCount = crossTable.get(0).count();
        for(int i = 0; i < noMatchingChecker.size(); i++){
            b = 0;
            for (Column it : table){
                crossTable.get(b++).setValue(crossColumnCount, it.getValue(noMatchingChecker.get(i)));
            }
            for(int j = 0; j < rightTable.getColumnCount(); j++){
                crossTable.get(b++).setValue(crossColumnCount, null);
            }
            crossColumnCount += 1;
        }
        return new TableImpl("crossInnerJoinTable", crossTable);
    }
    @Override
    public Table fullOuterJoin(Table rightTable, List<JoinColumn> joinColumns){
        int joinCount = joinColumns.size();
        Map<String, Integer> checker = new HashMap<>();
        List<Integer> thisChecker = new ArrayList<>();
        List<Integer> anotherChecker = new ArrayList<>();
        for (int i = 0; i < joinCount; i++) {
            String option1 = joinColumns.get(i).getColumnOfThisTable();
            String option2 = joinColumns.get(i).getColumnOfAnotherTable();
            Column thisColumn = this.getColumn(option1);
            Column anotherColumn = rightTable.getColumn(option2);
            int thisCount = thisColumn.count();
            int anotherCount = anotherColumn.count();
            for (int a = 0; a < thisCount; a++) {
                for (int b = 0; b < anotherCount; b++) {
                    if(thisColumn.getValue(a) == null || anotherColumn.getValue(b) == null){
                        if(thisColumn.getValue(a) == null){
                            break;
                        }
                        else{
                            continue;
                        }
                    }
                    if (thisColumn.getValue(a).equals(anotherColumn.getValue(b))) {
                        String equalColumn = a + "+" + b;
                        if (checker.get(equalColumn) == null) {
                            checker.put(equalColumn, 1);
                        } else {
                            checker.put(equalColumn, checker.get(equalColumn) + 1);

                        }
                        if (checker.get(equalColumn) == joinCount) {
                            thisChecker.add(a);
                            anotherChecker.add(b);
                            //System.out.println(b);

                        }
                    }
                }
            }
        }
        int u = 0;
        List<Integer> noMatchingChecker = new ArrayList<>();
        List<Integer> noMatchingAnotherChecker = new ArrayList<>();
        for(int y = 0; y < table.get(0).count() ; y++){
            if(u < thisChecker.size()){
                if(thisChecker.get(u) != y){
                    noMatchingChecker.add(y);
                }
                else{
                    u++;
                }}
            else noMatchingChecker.add(y);
        }
        u = 0;
        List<Integer> copyChecker = new ArrayList<>();
        copyChecker.addAll(anotherChecker);
        Collections.sort(copyChecker);
        for(int y = 0; y < rightTable.getRowCount(); y++){ // 5
            if(u < copyChecker.size()){
                if(copyChecker.get(u) == y){
                    u++;

                }
                else{
                    noMatchingAnotherChecker.add(y);

                }
            }
            else{
                noMatchingAnotherChecker.add(y);
            }
        }



        List<Column> crossTable = new ArrayList<>();
        for (Column it : table) {
            crossTable.add(new ColumnImpl(tableName + "." + it.getHeader()));
        }
        for (int i = 0; i < rightTable.getColumnCount(); i++) {
            crossTable.add(new ColumnImpl(rightTable.getName() + "." + rightTable.getColumn(i).getHeader()));
        }
        int b;
        for (int i = 0; i < thisChecker.size(); i++) {
            b = 0;
            for (Column it : table) {
                crossTable.get(b++).setValue(i, it.getValue(thisChecker.get(i)));
            }
            for (int x = 0; x < rightTable.getColumnCount(); x++) {
                crossTable.get(b++).setValue(i, rightTable.getColumn(x).getValue(anotherChecker.get(i)));
            }
        }
        int crossColumnCount = crossTable.get(0).count();
        for(int i = 0; i < noMatchingChecker.size(); i++){
            b = 0;
            for (Column it : table){
                crossTable.get(b++).setValue(crossColumnCount, it.getValue(noMatchingChecker.get(i)));
            }
            for(int j = 0; j < rightTable.getColumnCount(); j++){
                crossTable.get(b++).setValue(crossColumnCount, null);
            }
            crossColumnCount += 1;
        }
        for(int i = 0; i < noMatchingAnotherChecker.size(); i++){
            b = 0;
            for(int j = 0; j < table.size(); j++){
                crossTable.get(b++).setValue(crossColumnCount, null);
            }
            for(int k = 0; k < rightTable.getColumnCount(); k++){
                crossTable.get(b++).setValue(crossColumnCount, rightTable.getColumn(k).getValue(noMatchingAnotherChecker.get(i)));
            }
            crossColumnCount++;
        }
        return new TableImpl("crossInnerJoinTable", crossTable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableImpl table = (TableImpl) o;
        return tableName.equals(table.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName);
    }

    @Override
    public String toString() {
        return tableName+" has Rows : "+this.getRowCount()+", Columns : "+this.getColumnCount();
    }
}
