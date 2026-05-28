# TikTok Patches for Morphe

[![License: GPL v3](https://img.shields.io/badge/license-GPLv3-blue.svg)](LICENSE)
[![Morphe](https://img.shields.io/badge/morphe-patch%20source-00b894.svg)](https://github.com/MorpheApp/morphe-cli)
[![Platform](https://img.shields.io/badge/platform-Android-3ddc84.svg)](https://www.android.com/)
[![App](https://img.shields.io/badge/app-TikTok-ff0050.svg)](https://www.tiktok.com/)

<p>
  <a href="https://ko-fi.com/P5P5YOUU7">
    <img height="56" src="https://storage.ko-fi.com/cdn/kofi2.png?v=3" alt="Support my work on Ko-fi" />
  </a>
</p>

If this patch source saved you time, helped you keep an app usable, or gave you a base to build from, you can support my work on [Ko-fi](https://ko-fi.com/P5P5YOUU7).

This is a Morphe patch source for patching TikTok.

It exists because I wanted a TikTok patch set that is easier to build, test, publish, and keep focused on the current app versions I am working with.

This project is built on top of [RookieEnough/De-Vanced](https://github.com/RookieEnough/De-Vanced) and the [Morphe patches template](https://github.com/MorpheApp/morphe-patches-template). The goal here is not to pretend this started from nothing. The goal is to keep a clean Morphe source for the TikTok patches I am actively maintaining and adapting.

## Add Source

Add this source to Morphe with:

```text
https://morphe.software/add-source?github=just-a-weird-guy/tiktok-patches-for-morphe
```

Or manually:

```text
https://github.com/just-a-weird-guy/tiktok-patches-for-morphe
```

## Available Patches

- `Settings`  
  Adds the Morphe settings screen inside TikTok.

- `Enable Open Debug`  
  Adds a direct settings entry in TikTok so the Morphe settings screen can be opened reliably.

- `Disable login requirement`  
  Lets parts of TikTok load without forcing the normal login wall first.

- `Fix Google login`  
  Restores Google sign-in behavior after patching.

- `Feed filter`  
  Filters feed items such as ads, livestreams, stories, shop content, image videos, and videos outside configured view or like ranges.

- `Downloads`  
  Adds download support and download-related controls.

- `Playback speed`  
  Adds playback speed controls.

- `Remember clear display`  
  Keeps the clear-display state across videos.

- `SIM spoof`  
  Spoofs SIM-related values used by TikTok region logic.

- `Sanitize sharing links`  
  Cleans TikTok share links before they leave the app.

- `Show seekbar`  
  Shows the video seekbar where TikTok would normally hide it.

## Planned Work

- [/] Drama mini series feed video filter
- [/] Custom offline video download limit
- [ ] Remove create button
- [ ] Remove Tako AI
- [ ] Copy comments without the original commenter's username
- [ ] Feed tab navigation toggles for tabs like Friends, Explore, Following, and For You

## Project Structure

- [patches/](patches/)  
  Kotlin patch definitions, fingerprints, and shared patch utilities.

- [extensions/](extensions/)  
  Java extension code injected into TikTok by the patches.

- [patches-list.json](patches-list.json)  
  Generated patch metadata used by Morphe.

- [patches-bundle.json](patches-bundle.json)  
  Release metadata used by Morphe when adding this source.

## Building

Build the patch bundle with:

```bash
./gradlew :patches:build
```

Generate patch metadata with:

```bash
./gradlew :patches:generatePatchesList
```

The release workflow from the Morphe template handles generating release metadata when this is published through GitHub.

## Notes

- This is an unofficial TikTok patch source.
- It is not affiliated with TikTok, ByteDance, Morphe, ReVanced, or De-Vanced.
- TikTok changes often, so compatibility is tied to the versions listed in the patch metadata.

## License

This project reuses the GPLv3 licensing from the projects it was built on.

See [LICENSE](LICENSE) and [NOTICE](NOTICE).
