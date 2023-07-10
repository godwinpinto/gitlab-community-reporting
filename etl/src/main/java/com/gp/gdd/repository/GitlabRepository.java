package com.gp.gdd.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Common repository class to maintain all gitlab related queries
 */
@Repository
public class GitlabRepository {

    /**
     * Fetch Mongo db session
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Delete users collection from mongodb
     */
    public void deleteUsers() {
        mongoTemplate.dropCollection("users");
    }

    /**
     * Save json to users collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveUsers(List<? extends Document> documents) {
        mongoTemplate.getCollection("users").insertMany(documents);
    }

    /**
     * Delete issues collection from mongodb
     */
    public void deleteIssues() {
        mongoTemplate.dropCollection("issues");
    }

    /**
     * Save json to issues collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveIssues(List<? extends Document> documents) {
        mongoTemplate.getCollection("issues").insertMany(documents);
    }

    /**
     * Delete projects collection from mongodb
     */
    public void deleteProjects() {
        mongoTemplate.dropCollection("projects");
    }

    /**
     * Save json to projects collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveProjects(List<? extends Document> documents) {
        mongoTemplate.getCollection("projects").insertMany(documents);
    }

    /**
     * Delete groups collection from mongodb
     */
    public void deleteGroups() {
        mongoTemplate.dropCollection("groups");
    }

    /**
     * Save json to groups collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveGroups(List<? extends Document> documents) {
        mongoTemplate.getCollection("groups").insertMany(documents);
    }

    /**
     * Delete group users collection from mongodb
     */
    public void deleteGroupUsers() {
        mongoTemplate.dropCollection("group_users");
    }

    /**
     * Save json to group users collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveGroupUsers(List<? extends Document> documents) {
        mongoTemplate.getCollection("group_users").insertMany(documents);
    }

    /**
     * Fetch all groups ids from db
     */
    public List<Document> getAllGroups() {
        Query query = new Query();
        query.fields().include("id");
        return mongoTemplate.find(query, Document.class, "groups");
    }

    /**
     * Delete project users collection from mongodb
     */
    public void deleteProjectUsers() {
        mongoTemplate.dropCollection("project_users");
    }

    /**
     * Save json to project users collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveProjectUsers(List<? extends Document> documents) {
        mongoTemplate.getCollection("project_users").insertMany(documents);
    }

    /**
     * Delete project users collection from mongodb
     */
    public void deleteProjectBranches() {
        mongoTemplate.dropCollection("project_branches");
    }

    /**
     * Save json to project users collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveProjectBranches(List<? extends Document> documents) {
        mongoTemplate.getCollection("project_branches").insertMany(documents);
    }

    /**
     * Fetch all project ids from db
     */
    public List<Document> getAllProjects() {
        Query query = new Query();
        query.fields().include("id");
        return mongoTemplate.find(query, Document.class, "projects");
    }

    /**
     * Fetch all issues from db
     */
    public List<Document> getAllIssues() {
        Query query = new Query();
        query.fields().include("iid").include("id").include("project_id");
        /* query.addCriteria(Criteria.where("id").is(2837)); */
        return mongoTemplate.find(query, Document.class, "issues");
    }

    /**
     * Delete project users collection from mongodb
     */
    public void deleteIssueNotes() {
        mongoTemplate.dropCollection("issue_notes");
    }

    /**
     * Save json to project users collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveIssueNotes(List<? extends Document> documents) {
        mongoTemplate.getCollection("issue_notes").insertMany(documents);
    }

    /**
     * Delete group milestones from mongodb
     */
    public void deleteGroupMilestones() {
        mongoTemplate.dropCollection("group_milestones");
    }

    /**
     * Save json to group milestones collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveGroupMilestones(List<? extends Document> documents) {
        mongoTemplate.getCollection("group_milestones").insertMany(documents);
    }

    /**
     * Delete project milestones from mongodb
     */
    public void deleteProjectMilestones() {
        mongoTemplate.dropCollection("project_milestones");
    }

    /**
     * Save json to project milestones collection in bulk
     * 
     * @param documents mongodb compatible generic object
     */
    public void saveProjectMilestones(List<? extends Document> documents) {
        mongoTemplate.getCollection("project_milestones").insertMany(documents);
    }

}
