package ch.heig.dai.lab.protocoldesign;

public enum Operation {
    ADD(1),
    MUL(2),
    SUB(3),
    DIV(4),
    QUIT(5);

    private final int operation;

    Operation(int operation) {
        this.operation = operation;
    }

    public static void printMenuOptions(){
        System.out.println("Select an operation:");
        System.out.println("1. Addition");
        System.out.println("2. Multiplication");
        System.out.println("3. Subtraction");
        System.out.println("4. Division");
        System.out.println("5. Quit program");
        System.out.print("Enter your choice (1-5): ");
    }

    public static Operation getOperation(int value) {
        for (Operation option : Operation.values()) {
            if (option.operation == value) {
                return option;
            }
        }
        return null;
    }
}
