"use strict";

document.title = "TechFest 2026 | Developer Tools Lab";

console.log("TechFest Developer Tools lab script loaded.");

const workshopSeats = 80;
const registeredStudents = 53;
const availableSeats = workshopSeats - registeredStudents;

console.log("JavaScript calculation example:", {
    workshopSeats,
    registeredStudents,
    availableSeats
});

console.warn("Example warning: this message demonstrates a browser console warning.");
console.error("Example error: this message demonstrates how an error appears in the browser console.");
