package ru.amlet.config;

import ru.amlet.appcontainer.api.AppComponent;
import ru.amlet.appcontainer.api.AppComponentsContainerConfig;
import ru.amlet.services.EquationPreparer;
import ru.amlet.services.EquationPreparerImpl;
import ru.amlet.services.GameProcessor;
import ru.amlet.services.GameProcessorImpl;
import ru.amlet.services.IOService;
import ru.amlet.services.IOServiceConsole;
import ru.amlet.services.PlayerService;
import ru.amlet.services.PlayerServiceImpl;

@AppComponentsContainerConfig(order = 1)
public class AppConfig {

    @AppComponent(order = 0, name = "equationPreparer")
    public EquationPreparer equationPreparer(){
        return new EquationPreparerImpl();
    }

    @AppComponent(order = 1, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }

    @AppComponent(order = 2, name = "gameProcessor")
    public GameProcessor gameProcessor(IOService ioService,
                                       PlayerService playerService,
                                       EquationPreparer equationPreparer) {
        return new GameProcessorImpl(ioService, equationPreparer, playerService);
    }

    @AppComponent(order = 0, name = "ioService")
    public IOService ioService() {
        return new IOServiceConsole(System.out, System.in);
    }

}
