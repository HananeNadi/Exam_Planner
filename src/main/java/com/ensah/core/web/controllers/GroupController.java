package com.ensah.core.web.controllers;

import com.ensah.core.bo.Group;
import com.ensah.core.services.IGroupService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/Group")
public class GroupController {

    @Autowired
    IGroupService groupService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping
    public ResponseEntity<String> addGroupRS(@Valid @RequestBody Group group) {
        try {
            Long groupId = groupService.addGroup(group);
            logger.info("Group added successfully with ID: {}", groupId);
            return ResponseEntity.ok("Group added successfully with ID: " + groupId);
        } catch (RuntimeException e) {
            logger.error("Failed to add group: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to add group: " + e.getMessage());
        }
    }

    @PutMapping("/{groupnelId}")
    public ResponseEntity<String> updateGroupRS(@PathVariable Long groupnelId, @Valid @RequestBody Group groupDetails) {
        try {
            groupService.updateGroup(groupnelId, groupDetails);
            logger.info("Group with ID {} updated successfully.", groupnelId);
            return ResponseEntity.ok("Group updated successfully.");
        } catch (RuntimeException e) {
            logger.error("Failed to update group: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to update group: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllGroupRS() {
        logger.info("Fetching all groups.");
        List<Group> groups = groupService.getAllGroups();
        if (groups.isEmpty()) {
            logger.info("No groups found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No groups found.");
        }
        logger.info("Fetched {} groups.", groups.size());
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{groupnelId}")
    public ResponseEntity<?> getOneGroupRS(@PathVariable Long groupnelId) {
        logger.info("Fetching group with ID: {}", groupnelId);
        Group group = groupService.getGroupById(groupnelId);
        if (group == null) {
            logger.info("Group with ID {} not found.", groupnelId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Group with ID " + groupnelId + " not found.");
        }
        logger.info("Fetched group: {}", group);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{groupnelId}")
    public ResponseEntity<String> deleteGroupRS(@PathVariable Long groupnelId) {
        try {
            groupService.deleteGroup(groupnelId);
            logger.info("Group with ID {} has been deleted.", groupnelId);
            return ResponseEntity.ok("Group with ID " + groupnelId + " has been deleted.");
        } catch (RuntimeException e) {
            logger.error("Failed to delete group: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to delete group: " + e.getMessage());
        }
    }

    @PutMapping("/{groupId}/addProfessortogroup")
    public ResponseEntity<String> addProfessorsToGroup(@PathVariable Long groupId, @RequestBody Set<Long> professorIds) {
        try {
            groupService.addProfessorsToGroup(groupId, professorIds);
            logger.info("Professors added to group with ID {} successfully.", groupId);
            return ResponseEntity.ok("Professors added to group successfully.");
        } catch (RuntimeException e) {
            logger.error("Failed to add professors to group: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to add professors to group: " + e.getMessage());
        }
    }
}
