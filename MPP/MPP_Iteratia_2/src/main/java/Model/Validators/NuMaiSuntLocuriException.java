package Model.Validators;

public class NuMaiSuntLocuriException extends Exception{
    @Override
    public String getMessage() {
        return "Locuri insuficiente";
    }
}
