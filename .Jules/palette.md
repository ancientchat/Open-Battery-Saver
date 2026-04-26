## 2024-04-26 - Android Icon-Only Buttons Accessibility
**Learning:** Icon-only buttons in Android layouts (like ImageView with onClick listeners) often default to `android:contentDescription="@null"`, which makes them completely invisible to screen readers like TalkBack.
**Action:** Always verify that interactive ImageViews or ImageButtons use descriptive strings from `strings.xml` for `android:contentDescription` instead of `@null`.
