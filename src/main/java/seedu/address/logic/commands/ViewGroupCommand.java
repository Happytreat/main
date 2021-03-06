package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Lists all members of the group entered to the user.
 */
public class ViewGroupCommand extends Command {

    public static final String COMMAND_WORD = "view_group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View group members of a group"
            + "[" + PREFIX_NAME + " GROUP NAME]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_NAME + "Family ";

    public static final String MESSAGE_SUCCESS = "Listed all members:\n";

    private final Name groupName;

    /**
     * @param groupName of the group to list its members
     */
    public ViewGroupCommand(Name groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Group group = CommandUtil.retrieveGroupFromName(model, groupName);

        Predicate<Person> predicateShowAllGroupMembers = person -> group.getGroupMembers().contains(person);

        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        model.updateFilteredPersonList(predicateShowAllGroupMembers);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
