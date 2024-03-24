package controllers.PersonControllers;

import main.Utils;

public abstract class ControllerAbstract {
    protected Utils util;

    public ControllerAbstract() {
        util = new Utils();
    }
}
