package database;
import java.util.*;

class ColumnImpl implements  Column {
    private List<String> cell;
    private String header;
    private ListIterator<String> it;


    ColumnImpl(String header) {
        this.header = header;
        cell = new ArrayList<>();
        it = cell.listIterator();
    }
    @Override
    public String getHeader(){
        return header;
    }

    /* cell 값을 String으로 반환 */
    @Override
    public String getValue(int index){
        return cell.get(index);
    }

    /**
     * @param t Double.class, Long.class, Integer.class
     * @return cell 값을 타입 t로 반환, cell 값이 null이면 null 반환, 타입 t로 변환 불가능한 존재하는 값에 대해서는 예외 발생
     */
    @Override
    public <T extends Number> T getValue(int index, Class<T> t){
        T value;
        if (cell.get(index) == null) {
            return null;
        }
        else if(t.isAssignableFrom((Integer.class))){
            value = (T) Integer.valueOf(cell.get(index));
            return value;
        }
        else if(t.isAssignableFrom(Long.class)){
            value = (T) Long.valueOf(cell.get(index));
            return value;
        }
        else if(t.isAssignableFrom(Double.class)){
            value = (T) Double.valueOf(cell.get(index));
            return value;

        }
        else{
            throw new NumberFormatException("No type");
        }

    }

    @Override
    public void setValue(int index, String value){
        if(cell.size() == 0){
            cell.add(value);
        }
        if(cell.size() - 1 < index) {
            cell.add(value);
        }
        cell.set(index, value);
        it = cell.listIterator();
    }

    /**
     * @param value int 리터럴을 index의 cell 값으로 건네고 싶을 때 사용
     */
    @Override
    public void setValue(int index, int value){
        if(cell.size() == 0){
            cell.add(String.valueOf(value));
        }
        if(cell.size() - 1 < index) {
            cell.add(String.valueOf(value));
        }
        cell.set(index, String.valueOf(value));
        it = cell.listIterator();
    }

    /**
     * @return null 포함 모든 cell 개수 반환
     */
    @Override
    public int count(){
        return cell.size();
    }
    @Override
    public void show(){
        int maxLength = getMaxLength();
        if(!it.hasPrevious()){

            for(int i = header.length(); i < maxLength; i++){
                System.out.print(" ");
            }
            System.out.print(header+" | ");

        }
        else{
            String s = it.previous();
            if(s != null){
            for(int i = s.length(); i < maxLength; i++){
                System.out.print(" ");
            }
            System.out.print(s+" | ");}
            else{
                for(int i = 4; i < maxLength; i++){
                    System.out.print(" ");
                }
                System.out.print(s+" | ");
            }
           it.next();
        }
        if (it.hasNext()){
            it.next();
        }
    }

    /**
     * @return (int or null)로 구성된 컬럼 or (double or null)로 구성된 컬럼이면 true 반환
     */
    @Override
    public boolean isNumericColumn(){
        int check = 0;
        try{
        for(String it : cell){
            if (it == null){
                continue;
            }
            double d = Double.parseDouble(it);
        }}
        catch (NumberFormatException e){
            return false;
            }
        return true;

        }

    @Override
    public long getNullCount(){
        int check = 0;
        for (String it : cell){
            if (it == null){
                check++;
            }
        }
        return check;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnImpl column = (ColumnImpl) o;
        return header.equals(column.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header);
    }

    void add(String a){
        cell.add(a);
    }
    void add(int a){
        cell.add(String.valueOf(a));
    }

    private int getMaxLength(){
        int max = header.length();
        for(String it : cell){
            if(it != null){
            if(it.length() > max){
                max = it.length();
            }
            else continue;
        }}
        return max;
    }

}
