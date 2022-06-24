package discord;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.GameSDKException;
import de.jcm.discordgamesdk.activity.Activity;
import main.ExitTheDungeon;
import objects.Updating;

import java.io.File;
import java.time.Instant;

public class DiscordSDK {

    private Core core;
    private Thread callBacks;
    private boolean stop = false;

    public DiscordSDK(long id) {
        Core.init(new File("discord_game_sdk/lib/x86_64/discord_game_sdk.dll"));


        // Set parameters for the Core
        try(CreateParams params = new CreateParams()) {
            params.setClientID(id);
            params.setFlags(CreateParams.getDefaultFlags());
            // Create the Core
            try {
                core = new Core(params);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
                // Create the Activity
                try (Activity activity = new Activity()) {
                    activity.setDetails("ac");
                    activity.setState("ab");

                    // Setting a start time causes an "elapsed" field to appear
                    activity.timestamps().setStart(Instant.now());

                    // Make a "cool" image show up
                    activity.assets().setLargeImage("test");

                    // Finally, update the current activity to our activity
                    core.activityManager().updateActivity(activity);
                }
        }
        callBacks = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        core.runCallbacks();
                    } catch (GameSDKException ignored) {
                        System.out.println("lol");
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!stop) run();
                } catch (StackOverflowError sx) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!stop) run();
                }
            }
        });
        callBacks.start();
    }

    public Core getCore() {
        return core;
    }
}
