import java.util.ArrayList;
import java.util.List;

abstract class Event {
    private long eventTime;
    protected final long delayTime;

    Event(long delayTime) {
        this.eventTime = System.nanoTime();
        this.delayTime = delayTime;
        start();
    }

    void start() {
        eventTime = System.nanoTime() + delayTime;
    }

    boolean ready() {
        return System.nanoTime() >= eventTime;
    }

    abstract void action();
}

class Controller {
    private List<Event> eventList = new ArrayList<>();

    void addEvent(Event c) {
        eventList.add(c);
    }

    void run() throws InterruptedException {
        while (eventList.size() > 0) {
            for (Event e : new ArrayList<>(eventList)) {
                Thread.sleep(300);
                if (e.ready()) {
                    e.action();
                    eventList.remove(e);
                }
            }
        }
    }
}

public class GreenHouseController extends Controller {
    private boolean light = false;
    private boolean water = false;

    class LightOn extends Event {
        LightOn(long delayTime) {
            super(delayTime);
        }

        @Override
        void action() {
            light = true;
            System.out.println("light on");
        }
    }

    class LightOff extends Event {
        LightOff(long delayTime) {
            super(delayTime);
        }

        @Override
        void action() {
            light = false;
            System.out.println("light off");
        }
    }

    class WaterOn extends Event {
        WaterOn(long delayTime) {
            super(delayTime);
        }

        @Override
        void action() {
            water = true;
            System.out.println("water on");
        }
    }

    class WaterOff extends Event {
        WaterOff(long delayTime) {
            super(delayTime);
        }

        @Override
        void action() {
            water = false;
            System.out.println("water off");
        }
    }

    class Bell extends Event {
        Bell(long delayTime) {
            super(delayTime);
        }

        @Override
        void action() {
            System.out.println("...");
            addEvent(new Bell(delayTime));
        }
    }

    class Restart extends Event {
        private Event[] events;

        Restart(long delayTime, Event[] events) {
            super(delayTime);
            this.events = events;
        }

        @Override
        void action() {
            for (Event e : events) {
                e.start();
                addEvent(e);
            }
            start();
            addEvent(this);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GreenHouseController gc = new GreenHouseController();
        Event[] events = {
            gc.new LightOn(1000),
            gc.new LightOff(1500),
            gc.new WaterOn(2000),
            gc.new WaterOff(2500),
            gc.new Bell(3000)
        };
        gc.addEvent(gc.new Restart(5000, events));

        gc.run();
    }
}
