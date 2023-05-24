package red.civ.quarryplugin;

public class QuarryTask implements Runnable{

    Quarry q;

    public QuarryTask(Quarry q) {
    }

    @Override
    public void run() {

        if(q == null){
            Logger.Info("Skipping...");
        }
        else{
            q.dig();
        }

    }

}
