public class ComparatorTable extends ComparatorDefault {
    @Override
    public int compare(Object object1, Object object2) {

        Table leftTable = (Table) object1;
        Table rightTable = (Table) object2;

        // Handles nulls
        if (leftTable == null){
            return -1;
        } else if(rightTable == null){
            return 1;
        }

        if (leftTable.getTableSize() > rightTable.getTableSize()) {
            return 1;
        } else if (leftTable.getTableSize() < rightTable.getTableSize()) {
            return -1;
        }
        return 0;
    }
}
