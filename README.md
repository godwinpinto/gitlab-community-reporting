# GitLab Community Reporting (GCR)
Showcases how to extract gitlab data in MongoDB Atlas, Host on GCP and use reporting tool "Looker" to make more meaning out of your data

## Detailed information:
https://devpost.com/software/gitlab-ce-on-steriods-with-mongodb-atlas-and-google-cloud

## How to setup: 
1. Clone the repo
2. Navigate to etl directtory
3. Make sure the user being used has the top most group level and atleast maintainer access to get details of all groups / sub projects to download with the API. 
4. Gitlab API key: Go to profile->preference->access key-> tick (api, read_api, read_user, read_repository) and mark the expiry date and generate token
5. For super quick start with mongodb database test environment get a free instance at www.mongodb.com
6. define the environment variables i.e.
```shell
export GITLAB_API_URL=<YOUR GITLAB URL>
export GITLAB_TOKEN=<YOUR GITLAB TOKEN>
export MONGODB_URL=<YOUR MONGODB HOST URL>
export MONGODB_USER=<YOUR MONGODB USERNAME>
export MONGODB_PASSWORD=<YOUR MONGODB PASSWORD>
export MONGODB_NAME=<YOUR MONGODB DATABASE NAME>
```
7. Incase you need to setup the spring batch history into a permanent database. modify the database details accordingly in application.yml (datasource.url, etc)
8. mvn spring-boot:run to test changes
9. mvn package
10. Sample docker image is provided in the etl folder

## Known Limitations:
Gitlab API fetches max of 100 records at a time, you can tune the concurrency for processing in file com.gp.gdd.config.BatchConfiguration.java, search for "chunk"
