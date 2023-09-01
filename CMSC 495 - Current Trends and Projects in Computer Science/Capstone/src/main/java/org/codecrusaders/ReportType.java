/* Code Crusaders - Capstone Project
 *
 * Windows Security Event Log Parser
 *
 * University of Maryland Global Campus
 * CMSC 495: Current Trends and Projects in Computer Science
 * Professor Nevarez
 * July 11, 2023
 *
 * Development team:
 * Patrick Bell         - Project Lead, Programmer Back-End
 * Shallon Clippinger   - Technical Writer, Quality Assurance
 * Casey Lednum         - Documentation
 * Luis Moreno          - Programmer Front-End
 */

package org.codecrusaders;

/**
 * ReportType enums are used to assign descriptions to csv file information panels.
 */
public enum ReportType {
    FAILED_LOGINS, ADMIN_LOGINS, LOGINS, SCHEDULED_TASK_CREATED, UNDEFINED_EVENTS
}
