package red.civ.quarryplugin;

public class QuarryTask implements Runnable{

    Quarry q;

    public QuarryTask(Quarry qu) {
        q=qu;
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
