# Notes

## General

- [Android Vocabulary Glossary](https://developers.google.com/android/for-all/vocab-words/)

## Views

- [Material design](https://material.google.com/)
- [XML Visualizer](http://labs.udacity.com/android-visualizer/)
- [Common Views Cheatsheet](https://drive.google.com/file/d/0B5XIkMkayHgRMVljUVIyZzNmQUU/view)
- Make touch targets 48dp at least.
- Use `dp`for views.
- Use `sp` for fonts.
- [Recommended sizes for text](https://material.google.com/style/typography.html#typography-styles).
- [Use a standard text size with textAppearance](https://plus.google.com/+AndroidDevelopers/posts/gQuBtnuk6iG).
- [Color palette](https://material.google.com/style/color.html#color-color-palette).
- Use three or four styles no more.
- [Layout Weight](https://developer.android.com/guide/topics/ui/layout/linear.html) to distribute the remaining space in a LinearLayout. Ej: three TextView uniform distributed across the available vertical space: `android:layout_height="0dp"`and `android:layout_weight="1"`. If one of them must have double of space: `android:layout_weight="2"`.
- [RelativeLayout](https://developer.android.com/reference/android/widget/RelativeLayout.LayoutParams.html): relative to parent: `alignParentTop alignParentBottom alignParentLeft alignParentRight centerHorizontal centerVertical`.
- [RelativeLayout](https://developer.android.com/reference/android/widget/RelativeLayout.LayoutParams.html) relative to children: `layout_toRightOf layout_toLeftOf android:layout_below android:layout_above`.
- Use FrameLayout if we only have one child, it's more efficient.
- To use margin the View must be inside a ViewGroup.
- The attributes that start with "layout_" are handled by the parent ViewGroup, the rest of attributes are handled by the View.
- [Metrics & keylines](https://material.google.com/layout/metrics-keylines.html): All components should be aligned to an 8dp square baseline grid for mobile, tablet, and desktop. App to check the grid: [Keyline pushing](https://play.google.com/store/apps/details?id=com.faizmalkani.keylines&hl=en)
- `android:onClick="method"` to call a method when a button is clicked from XML.
- `android:gravity`sets the gravity of the content of the View its used on.
- `android:layout_gravity`sets the gravity of the View or Layout in its parent.
- Provide different layouts for different screen sizes.
- Provide different bitmap drawables for different screen densities.
- Use wrap_content, match_parent, or dp units when specifying dimensions in an XML layout file.
- Do not use hard coded pixel values in your application code.
- The system assumes that any default resources (those from a directory without configuration qualifiers) are designed for the baseline screen density (mdpi).
- Use `ScrollView` when the content can overflow the available space.
- Use [`Toast`](https://developer.android.com/guide/topics/ui/notifiers/toasts.html) in areas where System messages need to be displayed. (no user input, no action, no swiping).
- Use [`SnackBar`](https://developer.android.com/training/snackbar/showing.html) in areas where a simple popup message needs to be displayed along with an option to perform action. For Example: In GMail application, when you delete Mail, quick SnackBar display at the bottom with Message ‘1 Deleted’ with an action button ‘Undo’. On pressing the ‘Undo’ action button, the deleted mail will be restored. (inside activity, can perform actions, user input, can be dismissed by swiping).
- Use `CoordinatorLayout` that provides a superset of the functionality of `FrameLayout`.
- Styles apply to a View. Themes apply to the whole app or activity.
- [Material theme](https://developer.android.com/training/material/theme.html). To use the material theme in devices with Android<5.0, we have to use [AppCompat](https://developer.android.com/training/material/compatibility.html) from the support library.
- `colorPrimary` controls the action bar color and should be the main branding color. `colorPrimaryDark` colors the status bar. `colorAcent` used to accent views, such as checkboxes, editText and radioButtons.
- [Creating lists and cards](https://developer.android.com/training/material/lists-cards.html).
- If we want to find a view and we already have a reference to a parent view, we can call `parentView.findById(...)`. In this way, the method doesn't have to search the full hierarchy, just a subtree.

### Lists and adapters

- The Adapter itself is responsible for creating the Views that are displayed within the bound AdapterView (for example a ListView or GridView) - while the AdapterView is responsible for how those views are laid out.
- When the AdapterView needs to fill the list, it asks the Adapter which element is in position x, the adapter check the data and inflate the view for that data and pass it to the Adapter View, and so on. The AdapterView just load the number of elements that fit on the screen, plus three more (one to scroll up, another to scroll down and one extra to be recycled).

### Animations

- `android:animateLayoutChanges="true"`: a [layout animation](https://developer.android.com/training/animation/layout.html) is a pre-loaded animation that the system runs each time you make a change to the layout configuration.
- Google Photos like animations: [slides](https://photos.google.com/share/AF1QipMFXmZvx7yW0oaAgNMkjy4-BkQ9ikNHJ_-MwanFXWVAjKpoVcwAlUSDonqgt8e0ew?key=emRlWjh2M2drX1Q3dF9QdGFuZE1XMjdrMEJsaThn) | [repo](https://github.com/rallat/smokeAndMirrors)

### Configuration qualifiers

```
res/layout/my_layout.xml              // layout for normal screen size ("default")
res/layout-large/my_layout.xml        // layout for large screen size
res/layout-xlarge/my_layout.xml       // layout for extra-large screen size
res/layout-xlarge-land/my_layout.xml  // layout for extra-large in landscape orientation

res/drawable-mdpi/graphic.png         // bitmap for medium-density
res/drawable-hdpi/graphic.png         // bitmap for high-density
res/drawable-xhdpi/graphic.png        // bitmap for extra-high-density
res/drawable-xxhdpi/graphic.png       // bitmap for extra-extra-high-density

res/mipmap-mdpi/my_icon.png         // launcher icon for medium-density
res/mipmap-hdpi/my_icon.png         // launcher icon for high-density
res/mipmap-xhdpi/my_icon.png        // launcher icon for extra-high-density
res/mipmap-xxhdpi/my_icon.png       // launcher icon for extra-extra-high-density
res/mipmap-xxxhdpi/my_icon.png      // launcher icon for extra-extra-extra-high-density

```

### Screens

|                    | Low density (120), ldpi | Medium density (160), mdpi | High density (240), hdpi | Extra-high-density (320), xhdpi |
| ------------------ | ----------------------- | -------------------------- | ------------------------ | ------------------------------- |
| Small screen       | QVGA (240x320)          |                            | 480x640                  |                                 |
| Normal screen      | WQVGA400 (240x400)      | HVGA (320x480)             | WVGA800 (480x800)        | 640x960                         |
|                    | WQVGA432 (240x432)      |                            | WVGA854 (480x854)        |                                 |
|                    |                         |                            | 600x1024                 |                                 |
| Large screen       | WVGA800** (480x800)     | WVGA800* (480x800)         |                          |                                 |
|                    | WVGA854** (480x854)     | WVGA854* (480x854)         |                          |                                 |
|                    |                         | 600x1024                   |                          |                                 |
| Extra-Large screen | 1024x600                | WXGA (1280x800)†           | 1536x1152                | 2048x1536                       |
|                    |                         | 1024x768                   | 1920x1152                | 2560x1536                       |
|                    |                         | 1280x768                   | 1920x1200                | 2560x1600                       |

## Code

- First the layout XML is parsed and the is inflated creating a Java object by each View on the layout (TextView, ImageView, LinearLayout...). We can get these objects from our code thanks to the method `findViewById(resourceID)`. It returns a View object that you can cast to the specific type of view.
- Our activities should inherit from `AppCompatActivity`. It gives us backward compatibility support on older Android devices. It is part of the Android Support library. It also allows us to use the latest UI features on Android while still working on older devices.
- [Common Intents](https://developer.android.com/guide/components/intents-common.html).
- [Transmitting Network Data Using Volley](https://developer.android.com/training/volley/index.html).
- [Loading images from the web](https://github.com/bumptech/glide).
- [Creating a Background Service](https://developer.android.com/training/run-background-service/create-service.html).
- Google Play Services is a library of Google code that gives you access to popular functionality, such as phone location, authentication (information and ability to “log in"), Analytics and even fitness data.

### Logging

- e(String, String) (error)
- w(String, String) (warning)
- i(String, String) (information)
- d(String, String) (debug)
- v(String, String) (verbose)

### Localization

- To [support several languages](https://developer.android.com/training/basics/supporting-devices/languages.html) in an app, we have to create a `string.xml` file for each language:

```
MyProject/
    res/
       values/
           strings.xml
       values-es/
           strings.xml
       values-fr/
           strings.xml

```

- To retrieve a string: `String string = getString(R.string.hello);`. `getText()` will retain any rich text styling applied to the string.
- [Localization checklist](https://developer.android.com/distribute/tools/localization-checklist.html)

https://github.com/davidmigloz/android-development-for-beginners/wiki