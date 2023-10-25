package odogwudozilla.inecIrev;

import java.util.List;

public enum StatesEnum {

    ABIA ("1"),
    ADAMAWA ("2"),
    AKWA_IBOM ("3"),
    ANAMBRA ("4"),
    BAUCHI ("5"),
    BAYELSA ("6"),
    BENUE ("7"),
    BORNO ("8"),
    CROSS_RIVER ("9"),
    DELTA ("10"),
    EBONYI ("11"),
    EDO ("12"),
    EKITI ("13"),
    ENUGU ("14"),
    FCT ("15"),
    GOMBE ("16"),
    IMO ("17"),
    JIGAWA ("18"),
    KADUNA ("19"),
    KANO ("20"),
    KATSINA ("21"),
    KEBBI ("22"),
    KOGI ("23"),
    KWARA ("24"),
    LAGOS ("25"),
    NASARAWA ("26"),
    NIGER ("27"),
    OGUN ("28"),
    ONDO ("29"),
    OSUN ("30"),
    OYO ("31"),
    PLATEAU ("32"),
    RIVERS ("33"),
    SOKOTO ("34"),
    TARABA ("35"),
    YOBE ("36"),
    ZAMFARA ("37");

    public final String stateCode;

    StatesEnum(String stateCode) {
        this.stateCode = stateCode;
    }

    public static List<Enum<StatesEnum>> listOfStates() {
        return List.of(StatesEnum.values());
    }
}
