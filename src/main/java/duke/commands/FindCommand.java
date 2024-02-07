package duke.commands;
import java.util.List;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Command subclass for finding matching tasks
 */
public class FindCommand extends Command {

    /** Keyword to search for */
    private final String searchString;

    public FindCommand(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> resultList = tasks.search(searchString);
        ui.find(resultList);
    }
}
