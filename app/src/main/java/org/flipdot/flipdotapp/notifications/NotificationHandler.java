package org.flipdot.flipdotapp.notifications;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by daniel on 27.12.14.
 */
public abstract class NotificationHandler {
    public Context context;
    abstract void HandleNotification(Bundle parameters);
}
