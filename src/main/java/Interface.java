interface InterfaceA {
    void process();
}

interface InterfaceB {
    void process();
}

class ProcessorA implements InterfaceA, InterfaceB {

    @Override
    public void process() {
        System.out.println(this.getClass().getSimpleName());
    }

    public static void main(String[] args) {
        ProcessorA processor = new ProcessorA();
        ((InterfaceA) processor).process();
        ((InterfaceB) processor).process();
    }
}
