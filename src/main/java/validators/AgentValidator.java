package validators;

import model.Agent;

import java.util.ArrayList;
import java.util.List;

public class AgentValidator implements IValidator<Agent> {

    /**
     * Validate a Agent entity
     * name - string, name != ''
     * username - string, username's length > 7
     * password - string, password's length > 7 and contains both a-zA-Z and 0-9
     */
    @Override
    public void validate(Agent entity) throws ValidationException {
        String numRegex   = ".*[0-9].*";
        String alphaRegex = ".*[a-zA-Z].*";
        List<String> msgs = new ArrayList<String>();
        if (entity.getName().length() == 0) msgs.add("Name couldn't be vid!");
        if (entity.getUsername().length() < 7) msgs.add("The username should be at least 7 characters!");
        if (entity.getPassword().length() < 7) msgs.add("The password should be at least 7 characters!");
        if (!entity.getPassword().matches(numRegex) || !entity.getPassword().matches(alphaRegex))
            msgs.add("The password should contain both numbers and letters");
        if (msgs.size() > 0) {
            throw new ValidationException(msgs);
        }
    }
}
