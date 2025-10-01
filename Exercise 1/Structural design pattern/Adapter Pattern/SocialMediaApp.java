import java.util.Scanner;

interface SocialMediaPoster {
    void post(String message);
}

class TwitterAPI {
    public void sendTweet(String text) {
        System.out.println("Tweeted: " + text);
    }
}

class FacebookAPI {
    public void createPost(String content) {
        System.out.println("Facebook post: " + content);
    }
}

class TwitterAdapter implements SocialMediaPoster {
    private TwitterAPI twitter;

    public TwitterAdapter(TwitterAPI twitter) {
        this.twitter = twitter;
    }

    public void post(String message) {
        twitter.sendTweet(message);
    }
}

class FacebookAdapter implements SocialMediaPoster {
    private FacebookAPI facebook;

    public FacebookAdapter(FacebookAPI facebook) {
        this.facebook = facebook;
    }

    public void post(String message) {
        facebook.createPost(message);
    }
}

public class SocialMediaApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your message to post: ");
        String message = scanner.nextLine();

        System.out.print("Choose platform to post (1 - Twitter, 2 - Facebook): ");
        int choice = scanner.nextInt();

        SocialMediaPoster poster;
        if (choice == 1) {
            poster = new TwitterAdapter(new TwitterAPI());
        } else if (choice == 2) {
            poster = new FacebookAdapter(new FacebookAPI());
        } else {
            System.out.println("Invalid choice.");
            scanner.close();
            return;
        }

        poster.post(message);
        scanner.close();
    }
}
