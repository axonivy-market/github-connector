# GitHub Connector

An Axon Ivy REST API connector that integrates GitHub issues and workflows into your Ivy processes. This connector provides seamless communication with GitHub's REST API, enabling automated issue management, pull request handling, and workflow monitoring for Team Octopus and other development teams.

## Features

### Daily GitHub Issue Scan
- Automatically queries all open issues from a configured GitHub organization/repository
- Supports plannable execution schedules (daily, hourly, monthly, etc.)
- Retrieves comprehensive issue information for processing

### Smart Filter Logic
- Filters issues based on configurable criteria
- Supports keyword-based filtering to select relevant issues
- Customizable filtering logic for assignees and other attributes

### GitHub Issue Management
- **Issue Update**: Adds comments to GitHub issues directly from Ivy workflows
- **Automatic Assignment**: Assigns issues to configured watchers or teams
- **Patch Customization**: Override callable process to customize issue patching behavior

### Repository Services
- **Repository Service**: Full repository management capabilities
- **Pull Request Service**: Create, update, and manage pull requests
- **Workflow Run Service**: Monitor and interact with GitHub Actions workflows

### Issue Scanner Configuration
- Configurable scanning parameters
- Flexible organization and repository targeting
- Advanced search logic for issue discovery

## Demo

The GitHub Connector integrates seamlessly into Ivy workflows, enabling automated GitHub issue management:

1. **Automated Issue Scanning**: Schedule regular scans to discover new issues matching your criteria
2. **Smart Processing**: Filter and route issues based on keywords, labels, or assignees
3. **Automated Actions**: Automatically comment on, update, or assign issues
4. **Workflow Integration**: Connect GitHub Actions with your Ivy processes

## Setup

### Prerequisites
- Axon Ivy Engine 9.0 or later
- GitHub personal access token or OAuth token with appropriate permissions
- Access to a GitHub organization or repository

### Configuration

1. **GitHub Authentication**
   - Generate a GitHub personal access token with required scopes (`repo`, `issues`, `workflow`)
   - Configure the token in your Ivy project settings

2. **Organization/Repository Settings**
   - Set your target GitHub organization name
   - Configure repository(ies) to monitor

3. **Issue Scanner Configuration**
   - Define execution schedule (daily, hourly, monthly, etc.)
   - Configure filter logic:
     - Keywords to search for in issues
     - Assignee filters
     - Label-based filtering
   - Set watcher/team for automatic assignment

4. **Customization**
   - Override the callable process to customize issue patching behavior
   - Implement custom logic for specific workflows

### Getting Started

1. Install the GitHub Connector from the Axon Ivy Market
2. Configure your GitHub credentials and target repositories
3. Set up your issue scanner with desired filters
4. Customize workflows using the provided services
5. Schedule automated scans and let the connector handle GitHub integration

For detailed API documentation and advanced configuration options, refer to the connector's process documentation within your Ivy Designer.
