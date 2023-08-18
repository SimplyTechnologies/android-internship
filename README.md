# Birthday App

## Solution description

Technologies/libraries used
- Jetpack Compose to build the UI
- AndroidX Navigation for navigating between the screens
- MVVM architecture
- Kotlin Flow
- Koin for dependency injection
- Apollo Client for client-server communication
- Data Store as local database
- GraphQL to fetch the data from the server


## Source control naming conventions

### Branch naming

Branches are named using the following format: <br/>
`{prefix}/{task-description}`

**Prefix** can be:
- `feature`: User stories and Tasks
- `bug`: Bug fixes
- `chore`: Code improvements and changes that do not fit in categories above

**Task description** is a short and meaningful description of the ticket. It should be lowercase and should use dashes (-) as the delimiter.

Examples: <br/>
`feature/signup-form` <br/>
`bug/email-validation` <br/>
`chore/unused-imports-on-vehicle-screen` <br/>

### Commits

Commit message is a short description of the task and/or changes being introduced in that commit.

Example: <br/>
`Add vehicle hero image as background on home screen`

### Pull requests

PR name in general is the commit message. But can be edited to describe the PR changes better

Examples:<br/>
`Edit alert menu item subtitle text`

### PR descriptions

If a PR is associated with a ticket in our board, add a comment to the PR when creating it, that includes the title of a ticket and a link to it.

Example:<br/>
`[Users with outdated app are allowed to login via Biometrics](https://trello.com/c/2VXuWcUh/4-as-a-user-i-should-be-able-to-see-the-landing-screen)`<br/>
Adding a comment in this format will be rendered in markdown as a clickable text link to trello board