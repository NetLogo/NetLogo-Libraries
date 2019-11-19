# NetLogo Libraries

## What is it?

This repository houses the files that power NetLogo's Extension Manager.  If you want to make a new extension (or new version of an extension) available to the NetLogo community for download from the Extension Manager, this is the place to do it.  All that you need to do is submit [a pull request to this repository](https://github.com/NetLogo/NetLogo-Libraries/compare) and wait for us to merge it (which shouldn't take more than a few days, for a properly-formatted pull request).

## Submitting pull requests

**Please make sure that you are making your pull request to the correct branch!**  The 6.1 branch is *only* for extensions that work with NetLogo 6.1.x.  If your extension targets a different version of NetLogo, it must go on the appropriate branch for that version of NetLogo.  There was no Extension Manager before 6.1 (actually, before 6.0 development builds), so there cannot be any branches here for earlier versions of NetLogo.

Each pull request should have two parts:

  1. A `.zip` file added to the `extensions` directory
  2. A change to the `libraries.conf` file

For (1), the `.zip` file should have a name that follows the format `<extension name>-<version number>.zip`.  The `.zip` *must* contain `<extension name>.jar` and any other supporting files that accompany the extension (e.g. other `.jar` files, `.so` libraries).  **Make sure your jar files are in the "root" of the `.zip` file, not a subfolder.**

Regarding (2), if you are adding a new extension, you should make an insertion into `libraries.conf`, in alphabetical order according to the "name" field (e.g. "Fetch" comes after "Dist" and before "GoGo").  The new entry should follow this format:

```
{
    name: "The human-friendly name of the extension"
    codeName: "The name that will be used to refer to this extension in NetLogo code"
    shortDescription: "A one-sentence description of what the extension does"
    longDescription: """A full description of what the extension does"""
    version: "The current version number"
    homepage: "The URL that users may go to for documentation about this extension"
    downloadURL: "https://raw.githubusercontent.com/NetLogo/NetLogo-Libraries/<branch name>/extensions/<extension name>-<version number>.zip"
}
```

All of these fields are mandatory.  All values to the right of the colon should be wrapped in single-quotes (i.e. `"value"`).  `longDescription` may be wrapped in triple-quotes (i.e. `"""value"""`) in order to hold a multiline string.  Some editors also like to insert so-called smart-quotes (`“”`) around text, but our configuration file format requires straight quotes (`""`).

If you are simply uploading a new version (and not an entirely new extension), the only things that you usually need to change in `libraries.conf` are the extension's `version` field and its `downloadURL`.  And, of course, you still need to also include the new `.zip` file (as mentioned in (1)).

## Platforms

NetLogo supports Windows, macOS, and Linux operating systems and we prefer all extensions in the library support those platforms as well.  If you have libraries or components that cannot work one or two of the operating systems, you can still self-host your extension and distribute it the old fashioned way, by directing users extract the extension alongside a model or into the `extensions` folder under NetLogo.

## Email submission

If you do not want to use a pull request to add your extension to the library or to have it updated, you can instead email the CCL developer team at feedback@ccl.northwestern.edu.  You'll need to provide the same information as in the pull request along with the file package, but we'll take care of getting the repository updated.
