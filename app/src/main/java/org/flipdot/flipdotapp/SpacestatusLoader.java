package org.flipdot.flipdotapp;

/**
 * Created by daniel on 30.11.14.
 */
public class SpacestatusLoader {
    private static SpacestatusLoader _instance;

    public static SpacestatusLoader getInstance() {
        if(_instance == null) {
            _instance = new SpacestatusLoader();
        }

        return _instance;
    }

    private SpacestatusLoader() {
    }

    public Spacestatus getStatus() {
        SpacestatusLoadTask spacestatusLoadTask = new SpacestatusLoadTask();
        Spacestatus status = spacestatusLoadTask.doInBackground();
        return status;
    }
}
