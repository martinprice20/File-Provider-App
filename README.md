# File-Provider-App

This app allows users to create files with content in the app's local data storage location on their device. The user can choose the file name and content, but the file location is hard-coded.

The folder where the files are saved are authorised by the Android FileProvider service to be shared with other apps on the user's device. This is done via a share intent.

Users wishing to test this functionality should download the partner app for this POC project, the 'File-Client-App', which is set up to make requests to this app, and store the results.

When a user with a client app makes a request, the user is presented with a list of file Uri's present in the File-Provider-App's designated storage folder. By clicking on one of the items, the user chooses which file to request to import to the Client app. By clicking on 'Finish' button, the user completes the transfer of the file and closes the Provider App.
