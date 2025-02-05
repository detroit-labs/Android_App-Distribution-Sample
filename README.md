# App Distribution using Gradle, GitHub Actions and Firebase

This repository demonstrates how to automate app distribution using Gradle, GitHub Actions and Firebase. The workflow builds, signs, and distributes Android apps to Firebase App Distribution.

# Table of Contents

1. prerequisites
2. setup
3. github-actions-workflow
4. firebase-app-distribution

# Prerequisites

- GitHub account
- Firebase account
- Android Studio project set up with Firebase

# Setup

1. Create a new Firebase project and enable App Distribution.
2. Setup your build variants in the gradle file.
3. Configure your Android Studio project to use Firebase.

# GitHub Actions Workflow
This repository uses a GitHub Actions workflow to automate app distribution. The workflow consists of the following steps:

1. Build and sign the Android app
2. Upload to Github artifact or Firebase App Distribution console.

## Workflow File
The workflow file is located in the .github/workflows directory. You can modify the file to customize the workflow.

## Firebase App Distribution
This repository uses Firebase App Distribution to distribute the app to testers. You can view APK distributed in project "POC Mobile Deployment".

# Contributing
Contributions are welcome! If you'd like to contribute to this repository, please fork the repository and submit a pull request. For a more extensive expalanation of this repo see [accompaning documentation](https://docs.google.com/document/d/1sK68oUR_sfDC4-dMNgk5eDj2xu7C0Cw29EbtTue45UE/edit?pli=1&tab=t.0)
