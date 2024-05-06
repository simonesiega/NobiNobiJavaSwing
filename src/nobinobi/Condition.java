package nobinobi;

public class Condition {
    protected int flag;

    public Condition() {
        flag = 0;
    }
    public Condition(int c) {
        flag = c;
    }

    public boolean test(Condition c){
        return (flag % 2 == 0) ? ((c.getCondition() & flag) > 0) : ((c.getCondition() & (flag-1)) == flag-1);
    }

    public boolean isMarket(){
        if((flag & 2) == 2){
            return true;
        }
        return false;
    }

    public boolean isPort(){
        if((flag & 4) == 4){
            return true;
        }
        return false;
    }

    public boolean isCity(){
        if((flag & 8) == 8){
            return true;
        }
        return false;
    }

    public boolean isVillage(){
        if((flag & 16) == 16){
            return true;
        }
        return false;
    }

    public boolean isCastle(){
        if((flag & 32) == 32){
            return true;
        }
        return false;
    }

    public boolean isChurch(){
        if((flag & 64) == 64){
            return true;
        }
        return false;
    }

    public boolean isForest(){
        if((flag & 128) == 128){
            return true;
        }
        return false;
    }

    public boolean isPrairie(){
        if((flag & 256) == 256){
            return true;
        }
        return false;
    }

    public boolean isMountain(){
        if((flag & 512) == 512){
            return true;
        }
        return false;
    }

    public boolean isDesert(){
        if((flag & 1024) == 1024){
            return true;
        }
        return false;
    }

    public boolean isHill(){
        if((flag & 2048) == 2048){
            return true;
        }
        return false;
    }

    public boolean isSea(){
        if((flag & 4096) == 4096){
            return true;
        }
        return false;
    }

    public boolean isLake(){
        if((flag & 8192) == 8192){
            return true;
        }
        return false;
    }

    public boolean isRiver(){
        if((flag & 16384) == 16384){
            return true;
        }
        return false;
    }

    public boolean isMoon(){
        if((flag & 32768) == 32768){
            return true;
        }
        return false;
    }

    public boolean isSky(){
        if((flag & 65536) == 65536){
            return true;
        }
        return false;
    }

    public boolean isPNGSingle(){
        if((flag & 131072) == 131072){
            return true;
        }
        return false;
    }

    public boolean isPNGGroup(){
        if((flag & 262144) == 262144){
            return true;
        }
        return false;
    }

    public boolean isPNGFemale(){
        if((flag & 524288) == 524288){
            return true;
        }
        return false;
    }

    public boolean isPNGMale(){
        if((flag & 1048576) == 1048576){
            return true;
        }
        return false;
    }

    public int getCondition(){
        return flag;
    }

}
