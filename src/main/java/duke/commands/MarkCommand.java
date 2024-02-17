package duke.commands;

import duke.data.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Command subclass for marking or unmarking tasks
 */
public class MarkCommand extends Command {

    /** Index to mark or unmark */
    private final int updateIndex;
    /** Denotes whether to mark or unmark task */
    private boolean isComplete;

    /**
     * Constructor of MarkCommand
     * @param updateIndex task to update
     * @param isComplete whether to mark as done or not done
     */
    public MarkCommand(int updateIndex, boolean isComplete) {
        this.updateIndex = updateIndex;
        this.isComplete = isComplete;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            Task updateTask = tasks.markTask(updateIndex, isComplete);
            storage.saveTasks(tasks);
            return ui.mark(updateTask, isComplete);

        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    @Override
    public String undo(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (super.getIsUnDone()) {
            return "Last command was already undone";
        } else {
            this.isComplete = !isComplete;
            this.execute(tasks, ui, storage);
            return "Undo-ed last mark / unmark";
        }
    }
}
