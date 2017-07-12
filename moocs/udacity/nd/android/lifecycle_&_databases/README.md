# [Content Providers](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441)

*This chapter has been revised in February 2015 to reflect the updated course materials used with Sunshine-v2.*

### **Goals**:
1.


### **Code Steps (Sunshine-2) **
1. [Start Code Content Provider](https://github.com/udacity/Sunshine-Version-2/tree/4.07_start_code_content_provider)
2. [UriBuilder Weather With Location](https://github.com/udacity/Sunshine-Version-2/tree/4.08_uribuilder_weather_with_location)
8. [Write URI Matcher](https://github.com/udacity/Sunshine-Version-2/tree/4.09_write_uri_matcher)
9. [Register ContentProvider](https://github.com/udacity/Sunshine-Version-2/tree/4.10_register_contentprovider)
10. [GetType](https://github.com/udacity/Sunshine-Version-2/tree/4.11_gettype)
11. [Query](https://github.com/udacity/Sunshine-Version-2/tree/4.12_query)
12. [Insert](https://github.com/udacity/Sunshine-Version-2/tree/4.13_insert)
13. [Update_Delete](https://github.com/udacity/Sunshine-Version-2/tree/4.14_update_delete)
14. [Refactor FetchWeather](https://github.com/udacity/Sunshine-Version-2/tree/4.15_refactor_fetchweather)
15. [Use ContentProvider Inserts](https://github.com/udacity/Sunshine-Version-2/tree/4.16_use_contentprovider_inserts)
16. [BulkInserts with Content Provider](https://github.com/udacity/Sunshine-Version-2/tree/4.17_bulkinserts_with_contentprovider)


### **SubChapters**:
1. [Why Content Providers Matter](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-1637168804) (2:42)
2. [Create a Content Provider](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3657538714) (5:21)
3. [Grab the Starter Code](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3659838854)
4. [Explain our Changes to WeatherContract](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3650138769) (2:31)
5. [Add a Weather w/Location UriBuilder Quiz](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/e-3613508771/m-3669828609) (0:19)
6. [Understand the UriMatcher](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3612289061) (1:18)
7. [Write the UriMatcher Quiz](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/e-3655528847/m-3657218864) (0:21)
8. [Register the Content Provider Quiz](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/e-3642149158/m-3657978812) (1:19)
9. [Code the ContentProvider: onCreate](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3646848715) (1:34)
10. [Code the ContentProvider getType Quiz](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/e-3668928695/m-3670108636) (0:17)
11. [Code the ContentProvider query()](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3640118795) (1:07)
12. [Weather and Location queries Quiz](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/e-3645338921/m-3662388627) (0:15)
13. [Talking about our JOIN](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3667798749) (1:42)
14. [Code the ContentProvider.insert](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3656458890) (2:21)
15. [Finish Insert - Quiz](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/e-3649939414/m-3665218859) (0:14)
16. [Update and Delete Quiz](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/e-3628479143/m-3679538569) (0:22)
17. [Efficient Updates/Inserts](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3614409717) (0:52)
18. [ContentProvider Redux](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3655209144) (1:19)
19. [Create FetchWeather Task](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3612529384) (0:20)
20. [Refactoring FetchWeather Task](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3648058917) (notes)
21. [Inserts with ContentProvider - Quiz](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/e-3671668696/m-3647868739) (0:42)
22. [BulkInserts with ContentProvider - Quiz](https://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/e-3598049225/m-3632729235) (0:43)

### **Lesson Resources**
1. [Content Providers Background Lesson](https://www.udacity.com/course/viewer#!/c-ud258/l-3372188753/m-3409668668) (Udacity)
2. [UriBuilder Documentation](http://developer.android.com/reference/android/net/Uri.Builder.html)
3. [UriMatcher Documentation](http://developer.android.com/reference/android/content/UriMatcher.html)
4. [<provider> Documentation](http://developer.android.com/guide/topics/manifest/provider-element.html)
5. [Content Provider Basics](http://developer.android.com/guide/topics/providers/content-provider-basics.html#ClientProvider)
6. [parseId Documentation](http://developer.android.com/reference/android/content/ContentUris.html#parseId(android.net.Uri))

### **Notes**

1. [Why to not close the data base in the content provider](http://forums.udacity.com/questions/100269915/why-do-we-not-close-the-database-in-the-content-provider/100270484)

2. Libraries that deal with content providers/sqlite: [sqlite-provider](https://github.com/novoda/sqlite-provider), [sqlite-anaylzer](https://github.com/novoda/sqlite-analyzer), [sprinkles](https://github.com/emilsjolander/sprinkles), [greendao](https://github.com/greenrobot/greenDAO)

3. **Why Content Providers?** They allow you to share data across app boundaries by abstracting the data source; others can now see *what* the data contains without necessarily knowing *how* it is stored.

4. **Are Content Providers Necessary if I am within a single App?** Not necessary, but still recommended. The key idea here is to separate data model(what) from data logistics (where and how) such that you can decouple the user interfaces (leveraging the data) from the data storage option selected. And, this allows you to switch storage options or move that capability elsewhere later, without disrupting the UI code. *The default framework pattern is to assume the existence of a content provider for a data source*

5. SyncAdapters and CursorLoaders (which optimize the querying and loading of query results in UI elements) also rely on ContentProviders.

### **Bonus: [How to Use a Content Provider](https://www.udacity.com/course/viewer#!/c-ud258/l-3372188753/m-3409668668)**

How can two different apps read/write to the same data source (e.g., to Contacts)?
* Content Providers. They provide easily managed access to an underlying data source.
* Content Providers provide an extra layer of abstraction that allows *easy changing of underlying data source*
* Content Providers allow developers to *leverage functionality of existing helper classes* e.g., CursorAdapters, SyncAdapters, Loaders.
* Content Providers allow for multi-app access to a single shared data source in a *consistent and secure manner*

Working with the Android [User Dictionary](http://developer.android.com/reference/android/provider/UserDictionary.html) Provider.
At a high level, the setup for using Content Providers here looks like this:

![](https://lh5.ggpht.com/6DVpeuUp9ty8JQfBQPLQPTxtNr50qsOW49lwJJnJfZGqmUaqvjfwR3JvPUvf4AMdr7u_agGmVrvn1bjEygp5=s0#w=736&h=370)

For more information, refer to the following:
  * [Setting up DictionaryProviderExample](https://www.udacity.com/course/viewer#!/c-ud258/l-3372188753/m-3411088636) guide
  * [Android Developers: Content Provider Basics](http://developer.android.com/guide/topics/providers/content-provider-basics.html) Guide)

* **What is a ContentResolver?** A [ContentResolver](http://developer.android.com/reference/android/content/ContentResolver.html) helps your application connect with the *correct* ContentProvider for your needs. See ["Accessing the Content Provider"](https://www.udacity.com/course/viewer#!/c-ud258/l-3372188753/m-3383668991). Every application *Context* has an associated ContentResolver that you can access with *getContentResolver()*. The ContentResolver can then be used to access (query) a ContentProvider as follows:
```
ContentResolver resolver = getContentResolver();
Cursor cursor = resolver.query(
    UserDictionary.Words.CONTENT_URI,   // The content URI of the words table
    mProjection,                        // The columns to return for each row
    mSelectionClause                    // Selection criteria
    mSelectionArgs,                     // Selection criteria
    mSortOrder);                        // The sort order for the returned rows
);
```

* **What is a Content URI?**
A [content URI](http://developer.android.com/guide/topics/providers/content-provider-basics.html#ContentURIs) is a universal resource identifier that identifies the data in a provider. It typically includes the scheme (**content://**), the symbolic name of the provider (**authority**) and a name that points to the specific table (**path**) within it. e.g.,
```
content://user_dictionary/words
```
Here, *user_dictionary* is the provider's authority, and *words* is the specific table within that provider.

* **What is a CursorAdapter?** Recall that an Adapter is essentially a bridge between a data source (e.g., ArrayList) and a View (e.g., ListView) such that adding data to the data source automagically updates the corresponding View with an element containing that data. A *[CursorAdapter](http://developer.android.com/reference/android/widget/CursorAdapter.html)* is a specialized adapter that can expose data from a *[Cursor](http://developer.android.com/reference/android/database/Cursor.html)* to the associated ListView widget. The Cursor data **must** contain a column called *_id* for this to work. For an example of how CursorAdapter works, read the ["Android Developers: Displaying a Quick Contact Badge"](http://developer.android.com/training/contacts-provider/display-contact-badge.html) guide.

* **What is a Content Observer?** A [ContentObserver](http://developer.android.com/reference/android/database/ContentObserver.html) can be registered with a ContentResolver, to be notified if the data associated with a contentURI changes; in such cases, the observer then receives a callback.

* **What is a UriMatcher?** A [UriMatcher](http://developer.android.com/reference/android/content/UriMatcher.html) is a Utility class for allowing you to match a content URI against the specific type of data found in the associated Content Provider. In particular, it maps 'paths' within that ContentURI to integer constants -- now, given a URI, simply call *match(uri)* to determine the associated path instead of having to parse/manipulate path elements with complex if-else code. See [the docs](http://developer.android.com/reference/android/content/UriMatcher.html) for an example.

* **Steps to Building a Content Provider** ![](http://lh4.ggpht.com/xzBGPxfqtiViJZU89tuf7xPI9Xp7psIzg5bkGL2PPNmyxBjVYCSsCBQIeG20-OygFbd-1RmgO8iseVYB-Ae0=s0#w=1216&h=641)  The [starter code](xhttps://www.udacity.com/course/viewer#!/c-ud853/l-3599339441/m-3659838854) is available here.


### **Issues (Q&A)**

*We'll use this section to document and answer popular questions from study participants about this lesson.*
