package repository;

import model.Agent;

public interface IAgentRepository extends IRepository<Integer, Agent> {
    Agent findByUsernameAndPassword(String username, String password);
}
