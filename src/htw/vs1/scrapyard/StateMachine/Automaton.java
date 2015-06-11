package htw.vs1.scrapyard.StateMachine;

/**
 * Created by markus on 11.06.15.
 */
public class Automaton
{
    //Initial state
    private State state = State.S;

    public void setState(State s)
    {
        this.state = s;
    }

    public void readZero()
    {
        //Delegate...
        state.readZero(this);
    }

    public void readOne()
    {
        //Delegate...
        state.readOne(this);
    }

    public boolean isInFinalState()
    {
        //Delegate...
        return state.isFinal();
    }

    public static void main(String[] args)
    {
        Automaton a = new Automaton();
        String s1 = "00111110";
        for (char c : s1.toCharArray())
        {
            switch (c)
            {
                case '1':
                    a.readOne();
                    break;
                case '0':
                    a.readZero();
                    break;
            }
        }
        System.out.println(a.isInFinalState()); //true...
    }
}
