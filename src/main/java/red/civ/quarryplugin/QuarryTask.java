package red.civ.quarryplugin;

public class QuarryTask implements Runnable{

    @Override
    public void run() {
        QuarryCore.digAll();
    }

}
