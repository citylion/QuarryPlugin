package red.civ.quarryplugin;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class QuarryTask implements Runnable{

    @Override
    public void run() {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for(int i=0; i<QuarryCore.quarrylist.size();i++){
            if(QuarryCore.quarrylist.get(i).REMOVED){
                toRemove.add(0,i);//highest indexes put at front of list
            }
        }
        for(int k : toRemove){
            QuarryCore.quarrylist.remove(k);//highest indexes at front of list
        }

        QuarryCore.digAll();

    }

}
