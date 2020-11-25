package jolyjdia.bot.commands;

import jolyjdia.bot.objects.User;

public class ConsumerCommand {
    public User sender;
    public String[] args;

    public void accept(User sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }
}
