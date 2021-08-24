package mainpkg.settlement;

public class Place {

    private long code;
    private String name;
    private String status;

    public Place(long code, String name, String status){
        this.code = code;
        this.name = name;
        this.status = status;
    }


    public String getName(){
        return this.name;
    }

    public String getStatus(){
        return this.status;
    }

    public long getCode(){
        return this.code;
    }

    @Override
    public String toString(){
        return "Код: "+ this.code + " Статус: " + this.status + " Название: " + this.name;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Place place = (Place) obj;
        return code == place.code && (this.name.equals(place.getName())
                || (this.name != null && this.name.equals(place.getName()))) && (this.status.equals(place.getStatus())
                || (this.status != null && this.status.equals(place.getStatus())));
    }
}
