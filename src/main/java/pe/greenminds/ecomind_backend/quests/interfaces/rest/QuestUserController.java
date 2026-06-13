package pe.greenminds.ecomind_backend.quests.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllQuestUsersQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByQuestIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByUserIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestUserCommandService;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestUserQueryService;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateQuestUserResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.QuestUserResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.CreateQuestUserCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.QuestUserResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.UpdateQuestUserCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.resources.MessageResource;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*",
        methods = {RequestMethod.POST, RequestMethod.GET,
                RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/quest-users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Quest Users", description = "Quest User Management Endpoints")
public class QuestUserController {

    private final QuestUserCommandService questUserCommandService;
    private final QuestUserQueryService questUserQueryService;

    public QuestUserController(QuestUserCommandService questUserCommandService,
                                QuestUserQueryService questUserQueryService) {
        this.questUserCommandService = questUserCommandService;
        this.questUserQueryService = questUserQueryService;
    }

    @PostMapping
    public ResponseEntity<QuestUserResource> createQuestUser(@Valid @RequestBody CreateQuestUserResource resource) {
        var createQuestUserCommand = CreateQuestUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var questUserId = questUserCommandService.handle(createQuestUserCommand);
        var getQuestUserByIdQuery = new GetQuestUserByIdQuery(questUserId);
        var questUser = questUserQueryService.handle(getQuestUserByIdQuery);
        if (questUser.isPresent()) {
            var questUserResource = QuestUserResourceFromEntityAssembler.toResourceFromEntity(questUser.get());
            return new ResponseEntity<>(questUserResource, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<QuestUserResource>> getAllQuestUsers() {
        var getAllQuestUsersQuery = new GetAllQuestUsersQuery();
        var questUsers = questUserQueryService.handle(getAllQuestUsersQuery);
        var questUserResources = questUsers.stream()
                .map(QuestUserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(questUserResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestUserResource> getQuestUserById(@PathVariable Long id) {
        var getQuestUserByIdQuery = new GetQuestUserByIdQuery(id);
        var questUser = questUserQueryService.handle(getQuestUserByIdQuery);
        if (questUser.isPresent()) {
            var questUserResource = QuestUserResourceFromEntityAssembler.toResourceFromEntity(questUser.get());
            return ResponseEntity.ok(questUserResource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<QuestUserResource>> getQuestUsersByUserId(@PathVariable Long userId) {
        var getQuestUserByUserIdQuery = new GetQuestUserByUserIdQuery(userId);
        var questUsers = questUserQueryService.handle(getQuestUserByUserIdQuery);
        var questUserResources = questUsers.stream()
                .map(QuestUserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(questUserResources);
    }

    @GetMapping("/by-quest/{questId}")
    public ResponseEntity<List<QuestUserResource>> getQuestUsersByQuestId(@PathVariable Long questId) {
        var getQuestUserByQuestIdQuery = new GetQuestUserByQuestIdQuery(questId);
        var questUsers = questUserQueryService.handle(getQuestUserByQuestIdQuery);
        var questUserResources = questUsers.stream()
                .map(QuestUserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(questUserResources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestUserResource> updateQuestUser(@PathVariable Long id, @Valid @RequestBody QuestUserResource resource) {
        var updateQuestUserCommand = UpdateQuestUserCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var questUser = questUserCommandService.handle(updateQuestUserCommand);
        if (questUser.isPresent()) {
            var questUserResource = QuestUserResourceFromEntityAssembler.toResourceFromEntity(questUser.get());
            return ResponseEntity.ok(questUserResource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResource> deleteQuestUser(@PathVariable Long id) {
        questUserCommandService.handle(new DeleteQuestUserCommand(id));
        return ResponseEntity.ok(new MessageResource("QuestUser with id " + id + " deleted successfully"));
    }
}
