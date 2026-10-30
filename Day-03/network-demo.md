# Day 03 Network Tab Results

**Day:** 03  
**Title:** How the Web Works  
**Lab ID:** JFS-M2-01  
**Status:** Completed  
**Checks passed:** 6/6

The following values are the recorded results from the Day 03 browser Network tab inspection.

| Resource | Status | Type | Size | Time |
| --- | ---: | --- | ---: | ---: |
| `index.html` | 200 | document | 7.2 kB | 95 ms |
| `style.css` | 200 | stylesheet | 18 kB | 48 ms |
| `app.js` | 200 | script | 31 kB | 62 ms |
| `hero-banner.jpg` | 200 | jpeg | 3.4 MB | 2.6 s |
| `sponsor-logo.png` | 404 | png | 0.2 kB | 18 ms |
| `events` | 200 | fetch | 1.1 kB | 140 ms |

## Summary

- **Total requests:** 6
- **Transferred:** 3.5 MB
- **DOMContentLoaded:** 0.41 seconds
- **Load:** 2.9 seconds
- **Missing resource:** `sponsor-logo.png` returned 404.
- **Slowest resource:** `hero-banner.jpg` took 2.6 seconds.
- **API request:** `events` returned status 200 as a fetch request.
- **Suggested improvement:** Compress or resize `hero-banner.jpg` to reduce its transfer size and loading time.

The observed Network panel identifies the JavaScript resource as `app.js`. This folder's self-contained demonstration page uses `script.js`; these are distinct contexts and the recorded Network results above have not been changed.
