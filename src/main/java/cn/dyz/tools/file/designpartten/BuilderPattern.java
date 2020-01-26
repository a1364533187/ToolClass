package cn.dyz.tools.file.designpartten;

public class BuilderPattern {

    public static void main(String[] args) {
        InstallEmployee installEmployee = new InstallEmployee();
        Boss b = new Boss(installEmployee);
        System.out.println("computer: " + b.createComputer("i7", "6G", "360G", "huawei"));

    }
}

//product
class Computer {

    private String cpu;
    private String hardDisk;
    private String mainBoard;
    private String memory;

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }

    public String getMainBoard() {
        return mainBoard;
    }

    public void setMainBoard(String mainBoard) {
        this.mainBoard = mainBoard;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Computer{" + "cpu='" + cpu + '\'' + ", hardDisk='" + hardDisk + '\''
                + ", mainBoard='" + mainBoard + '\'' + ", memory='" + memory + '\'' + '}';
    }
}

//boss 只需要只会员工去安装电脑就好了， 派活
class Boss {

    private InstallEmployee installEmployee;

    Boss(InstallEmployee installEmployee) {
        this.installEmployee = installEmployee;
    }

    public Computer createComputer(String cpu, String memory, String hardDisk, String mainBoard) {
        installEmployee.createCpu(cpu);
        installEmployee.createHardDisk(hardDisk);
        installEmployee.createMainBoard(mainBoard);
        installEmployee.createMemory(memory);
        return installEmployee.createComputer();
    }
}

abstract class Install {

    abstract void createCpu(String cpu);

    abstract void createMemory(String memory);

    abstract void createMainBoard(String mainBoard);

    abstract void createHardDisk(String hardDisk);

    void createkeyBoard(String keyBoard) {
    };
}

class InstallEmployee extends Install {

    private Computer computer;

    InstallEmployee() {
        computer = new Computer();
    }

    @Override
    void createCpu(String cpu) {
        computer.setCpu(cpu);
    }

    @Override
    void createMainBoard(String mainBoard) {
        computer.setCpu(mainBoard);
    }

    @Override
    void createMemory(String memory) {
        computer.setMemory(memory);
    }

    @Override
    void createHardDisk(String hardDisk) {
        computer.setHardDisk(hardDisk);
    }

    Computer createComputer() {
        return computer;
    }
}
