package model;

public class TripModel {

    private Integer id;
    private Integer originX;
    private Integer originY;
    private Integer destX;
    private Integer destY;
    private Integer stepStart;
    private Integer stepFinish;

    public Double getScore(){
        double score = new Double(getDistance()) / new Double(getDuration());
        if(score > 1)
            score = 0;

        return score;
    }

    public Integer getDuration(){
        return stepFinish - stepStart;
    }

    public Integer getDistance(){
        return Math.abs(originX - destX) + Math.abs(originY - destY);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginX() {
        return originX;
    }

    public void setOriginX(Integer originX) {
        this.originX = originX;
    }

    public Integer getOriginY() {
        return originY;
    }

    public void setOriginY(Integer originY) {
        this.originY = originY;
    }

    public Integer getDestX() {
        return destX;
    }

    public void setDestX(Integer destX) {
        this.destX = destX;
    }

    public Integer getDestY() {
        return destY;
    }

    public void setDestY(Integer destY) {
        this.destY = destY;
    }

    public Integer getStepStart() {
        return stepStart;
    }

    public void setStepStart(Integer start) {
        this.stepStart = start;
    }

    public Integer getStepFinish() {
        return stepFinish;
    }

    public void setStepFinish(Integer finish) {
        this.stepFinish = finish;
    }
}
