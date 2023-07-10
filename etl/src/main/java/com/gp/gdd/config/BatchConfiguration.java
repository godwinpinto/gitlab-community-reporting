package com.gp.gdd.config;

import org.bson.Document;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.gp.gdd.collections.groups.GroupsItemProcessor;
import com.gp.gdd.collections.groups.GroupsItemReader;
import com.gp.gdd.collections.groups.GroupsItemWriter;
import com.gp.gdd.collections.groups.milestones.GroupMilestonesItemProcessor;
import com.gp.gdd.collections.groups.milestones.GroupMilestonesItemReader;
import com.gp.gdd.collections.groups.milestones.GroupMilestonesItemWriter;
import com.gp.gdd.collections.groups.users.GroupUsersItemProcessor;
import com.gp.gdd.collections.groups.users.GroupUsersItemReader;
import com.gp.gdd.collections.groups.users.GroupUsersItemWriter;
import com.gp.gdd.collections.issues.IssuesItemProcessor;
import com.gp.gdd.collections.issues.IssuesItemReader;
import com.gp.gdd.collections.issues.IssuesItemWriter;
import com.gp.gdd.collections.issues.notes.IssueNotesItemProcessor;
import com.gp.gdd.collections.issues.notes.IssueNotesItemReader;
import com.gp.gdd.collections.issues.notes.IssueNotesItemWriter;
import com.gp.gdd.collections.projects.ProjectsItemProcessor;
import com.gp.gdd.collections.projects.ProjectsItemReader;
import com.gp.gdd.collections.projects.ProjectsItemWriter;
import com.gp.gdd.collections.projects.branches.ProjectBranchesItemProcessor;
import com.gp.gdd.collections.projects.branches.ProjectBranchesItemReader;
import com.gp.gdd.collections.projects.branches.ProjectBranchesItemWriter;
import com.gp.gdd.collections.projects.milestones.ProjectMilestonesItemProcessor;
import com.gp.gdd.collections.projects.milestones.ProjectMilestonesItemReader;
import com.gp.gdd.collections.projects.milestones.ProjectMilestonesItemWriter;
import com.gp.gdd.collections.projects.users.ProjectUsersItemProcessor;
import com.gp.gdd.collections.projects.users.ProjectUsersItemReader;
import com.gp.gdd.collections.projects.users.ProjectUsersItemWriter;
import com.gp.gdd.collections.users.UsersItemProcessor;
import com.gp.gdd.collections.users.UsersItemReader;
import com.gp.gdd.collections.users.UsersItemWriter;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

/**
 * Steps and Job Configurations are defined here
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;

        @Autowired
        UsersItemReader usersItemReader;
        @Autowired
        UsersItemProcessor usersItemProcessor;
        @Autowired
        UsersItemWriter usersItemWriter;

        @Autowired
        IssuesItemReader issuesItemReader;
        @Autowired
        IssuesItemProcessor issuesItemProcessor;
        @Autowired
        IssuesItemWriter issuesItemWriter;

        @Autowired
        IssueNotesItemReader issueNotesItemReader;
        @Autowired
        IssueNotesItemProcessor issueNotesItemProcessor;
        @Autowired
        IssueNotesItemWriter issueNotesItemWriter;

        @Autowired
        GroupsItemReader groupsItemReader;
        @Autowired
        GroupsItemProcessor groupsItemProcessor;
        @Autowired
        GroupsItemWriter groupsItemWriter;

        @Autowired
        GroupUsersItemReader groupUsersItemReader;
        @Autowired
        GroupUsersItemProcessor groupUsersItemProcessor;
        @Autowired
        GroupUsersItemWriter groupUsersItemWriter;

        @Autowired
        GroupMilestonesItemReader groupMilestonesItemReader;
        @Autowired
        GroupMilestonesItemProcessor groupMilestonesItemProcessor;
        @Autowired
        GroupMilestonesItemWriter groupMilestonesItemWriter;

        @Autowired
        ProjectsItemReader projectsItemReader;
        @Autowired
        ProjectsItemProcessor projectsItemProcessor;
        @Autowired
        ProjectsItemWriter projectsItemWriter;

        @Autowired
        ProjectUsersItemReader projectUsersItemReader;
        @Autowired
        ProjectUsersItemProcessor projectUsersItemProcessor;
        @Autowired
        ProjectUsersItemWriter projectUsersItemWriter;

        @Autowired
        ProjectMilestonesItemReader projectMilestonesItemReader;
        @Autowired
        ProjectMilestonesItemProcessor projectMilestonesItemProcessor;
        @Autowired
        ProjectMilestonesItemWriter projectMilestonesItemWriter;

        @Autowired
        ProjectBranchesItemReader projectBranchesItemReader;
        @Autowired
        ProjectBranchesItemProcessor projectBranchesItemProcessor;
        @Autowired
        ProjectBranchesItemWriter projectBranchesItemWriter;

        /**
         * gitlab users collection step
         * 
         * @return Step
         */
        @Bean
        public Step usersCollectionStep() {
                return stepBuilderFactory.get("stepUsers").<Document, Document>chunk(1)
                                .reader(usersItemReader).processor(usersItemProcessor)
                                .writer(usersItemWriter).build();
        }

        /**
         * Gitlab issues step
         * 
         * @return Step
         */
        @Bean
        public Step issuesCollectionStep() {
                return stepBuilderFactory.get("stepIssues").<Document, Document>chunk(10)
                                .reader(issuesItemReader).processor(issuesItemProcessor)
                                .writer(issuesItemWriter).build();
        }

        /**
         * gitlab group collection step
         * 
         * @return Step
         */
        @Bean
        public Step groupsCollectionStep() {
                return stepBuilderFactory.get("stepGroups").<Document, Document>chunk(1)
                                .reader(groupsItemReader).processor(groupsItemProcessor)
                                .writer(groupsItemWriter).build();
        }

        /**
         * gitlab project collection step
         * 
         * @return Step
         */
        @Bean
        public Step projectsCollectionStep() {
                return stepBuilderFactory.get("stepProjects").<Document, Document>chunk(1)
                                .reader(projectsItemReader).processor(projectsItemProcessor)
                                .writer(projectsItemWriter).build();
        }

        /**
         * gitlab users inside a group collection step
         * 
         * @return Step
         */
        @Bean
        public Step groupUsersCollectionStep() {
                return stepBuilderFactory.get("stepGroupUsers").<Document, Document>chunk(1)
                                .reader(groupUsersItemReader).processor(groupUsersItemProcessor)
                                .writer(groupUsersItemWriter).build();
        }

        /**
         * gitlab users inside a project collection step
         * 
         * @return Step
         */
        @Bean
        public Step projectUsersCollectionStep() {
                return stepBuilderFactory.get("stepProjectUsers").<Document, Document>chunk(1)
                                .reader(projectUsersItemReader).processor(projectUsersItemProcessor)
                                .writer(projectUsersItemWriter).build();
        }

        /**
         * milestones mapped to project collection step
         * 
         * @return Step
         */
        @Bean
        public Step projectMilestonesCollectionStep() {
                return stepBuilderFactory.get("stepProjectMilestones").<Document, Document>chunk(1)
                                .reader(projectMilestonesItemReader)
                                .processor(projectMilestonesItemProcessor)
                                .writer(projectMilestonesItemWriter).build();
        }

        /**
         * milestones mapped to group collection step
         * 
         * @return Step
         */
        @Bean
        public Step groupMilestonesCollectionStep() {
                return stepBuilderFactory.get("stepGroupMilestones").<Document, Document>chunk(1)
                                .reader(groupMilestonesItemReader)
                                .processor(groupMilestonesItemProcessor)
                                .writer(groupMilestonesItemWriter).build();
        }

        /**
         * repositories inside project collection step
         * 
         * @return Step
         */
        @Bean
        public Step projectBranchesCollectionStep() {
                return stepBuilderFactory.get("stepProjectBranches").<Document, Document>chunk(1)
                                .reader(projectBranchesItemReader)
                                .processor(projectBranchesItemProcessor)
                                .writer(projectBranchesItemWriter).build();
        }

        /**
         * projects collection flow with dependant data structure being fetched on next
         * step
         * 
         * @return
         */
        @Bean
        public Flow projectsFlow() {
                return new FlowBuilder<SimpleFlow>("flowProjects").start(projectsCollectionStep())
                                .next(subProjectsFlow()).build();
        }

        /**
         * project dependant data being fetch in parallel
         * 
         * @return
         */
        @Bean
        public Flow subProjectsFlow() {
                return new FlowBuilder<SimpleFlow>("flowSubProjects")
                                .split(new SimpleAsyncTaskExecutor("AsyncParallelSubProjects"))
                                .add(new FlowBuilder<SimpleFlow>("flowProjectUsers")
                                                .start(projectUsersCollectionStep()).build(),
                                                new FlowBuilder<SimpleFlow>("flowProjectBranches")
                                                                .start(projectBranchesCollectionStep())
                                                                .build(),
                                                new FlowBuilder<SimpleFlow>("flowProjectMilestones")
                                                                .start(projectMilestonesCollectionStep())
                                                                .build())
                                .build();
        }

        /**
         * groups collection flow with dependant data structure being fetched on next
         * step
         * 
         * @return
         */
        @Bean
        public Flow groupsFlow() {
                return new FlowBuilder<SimpleFlow>("flowGroups").start(groupsCollectionStep())
                                .next(subGroupsFlow()).build();
        }

        /**
         * group dependant data being fetch in parallel
         * 
         * @return
         */
        @Bean
        public Flow subGroupsFlow() {
                return new FlowBuilder<SimpleFlow>("flowSubGroups")
                                .split(new SimpleAsyncTaskExecutor("AsyncParallelSubGroups"))
                                .add(new FlowBuilder<SimpleFlow>("flowGroupUsers")
                                                .start(groupUsersCollectionStep()).build(),
                                                new FlowBuilder<SimpleFlow>("flowGroupMilestones")
                                                                .start(groupMilestonesCollectionStep())
                                                                .build())
                                .build();
        }

        /**
         * step to fetch all issues from gitlab
         * 
         * @return
         */
        @Bean
        public Flow issuesFlow() {
                return new FlowBuilder<SimpleFlow>("flowIssues").start(issuesCollectionStep())
                                .next(new FlowBuilder<SimpleFlow>("flowIssueNotes")
                                                .start(issueNotesCollectionStep()).build())
                                .build();
        }

        /**
         * notes related to issue collection step
         * 
         * @return Step
         */
        @Bean
        public Step issueNotesCollectionStep() {
                return stepBuilderFactory.get("stepIssueNotes").<Document, Document>chunk(50)
                                .reader(issueNotesItemReader).processor(issueNotesItemProcessor)
                                .writer(issueNotesItemWriter).build();
        }

        /**
         * base flow to kickstart the job where steps can run in parallel
         * 
         * @return
         */
        @Bean
        public Flow baseFlow() {
                return new FlowBuilder<SimpleFlow>("flowBase")
                                .split(new SimpleAsyncTaskExecutor("AsyncParallelBase"))
                                .add(new FlowBuilder<SimpleFlow>("flowUsers")
                                                .start(usersCollectionStep()).build()
                                        ,
                                                projectsFlow(), groupsFlow(), issuesFlow()
                                )
                                .build();
        }

        /**
         * Main job invocation logic
         * 
         * @return Job
         */
        @Bean
        public Job jobProcessGitData() {
                return jobBuilderFactory.get("jobProcessGitData").start(baseFlow()).end().build();
        }

}
