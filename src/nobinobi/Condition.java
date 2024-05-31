package nobinobi;

/**
 * Classe che implementa una condizione
 */
public class Condition {

    // Flag
    protected int flag;

    /**
     * Costruttore vuoto
     * Inizializza tutti gli attributi vuoti
     */
    public Condition() {
        flag = 0;
    }

    /**
     * Costruttore di condition
     * @param c Flag
     */
    public Condition(int c) {
        flag = c;
    }

    /**
     * 1 / tutti i check
     * @param c condizione da confrontare
     * @return true o false
     */
    public boolean test(Condition c){
        return (flag % 2 == 0) ? ((c.getCondition() & flag) > 0) : ((c.getCondition() & (flag-1)) == flag-1);
    }

    /*
    * Controlla tutti i check del flag
    * controlla ogni bit
    * controllare path
    * src/saves/dates/BitCondition.png
    */

    public boolean isMarket(){
        return (flag & 2) == 2;
    }

    public boolean isPort(){
        return (flag & 4) == 4;
    }

    public boolean isCity(){
        return (flag & 8) == 8;
    }

    public boolean isVillage(){
        return (flag & 16) == 16;
    }

    public boolean isCastle(){
        return (flag & 32) == 32;
    }

    public boolean isChurch(){
        return (flag & 64) == 64;
    }

    public boolean isForest(){
        return (flag & 128) == 128;
    }

    public boolean isPrairie(){
        return (flag & 256) == 256;
    }

    public boolean isMountain(){
        return (flag & 512) == 512;
    }

    public boolean isDesert(){
        return (flag & 1024) == 1024;
    }

    public boolean isHill(){
        return (flag & 2048) == 2048;
    }

    public boolean isSea(){
        return (flag & 4096) == 4096;
    }

    public boolean isLake(){
        return (flag & 8192) == 8192;
    }

    public boolean isRiver(){
        return (flag & 16384) == 16384;
    }

    public boolean isMoon(){
        return (flag & 32768) == 32768;
    }

    public boolean isSky(){
        return (flag & 65536) == 65536;
    }

    public boolean isPNGSingle(){
        return (flag & 131072) == 131072;
    }

    public boolean isPNGGroup(){
        return (flag & 262144) == 262144;
    }

    public boolean isPNGFemale(){
        return (flag & 524288) == 524288;
    }

    public boolean isPNGMale(){
        return (flag & 1048576) == 1048576;
    }

    // Getter di flag
    public int getCondition(){
        return flag;
    }
}
