package pe.greenminds.ecomind_backend.quests.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllQuestsQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.SearchQuestQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Theme;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestCommandService;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestQueryService;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateQuestResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.QuestResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.CreateQuestCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.QuestResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.UpdateQuestCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.resources.MessageResource;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*",
        methods = {RequestMethod.POST, RequestMethod.GET,
                RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/quests", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Quests", description = "Quest Management Endpoints")
public class QuestController {
    private final QuestCommandService questCommandService;
    private final QuestQueryService questQueryService;

    public QuestController(QuestCommandService questCommandService, QuestQueryService questQueryService) {
        this.questCommandService = questCommandService;
        this.questQueryService = questQueryService;
    }

    @PostMapping
    public ResponseEntity<QuestResource> createQuest(@Valid @RequestBody CreateQuestResource resource) {
        var createQuestCommand = CreateQuestCommandFromResourceAssembler.toCommandFromResource(resource);
        var questId = questCommandService.handle(createQuestCommand);
        var getQuestByIdQuery = new GetQuestByIdQuery(questId);
        var quest = questQueryService.handle(getQuestByIdQuery);
        if (quest.isPresent()) {
            var questResource = QuestResourceFromEntityAssembler.toResourceFromEntity(quest.get());
            return new ResponseEntity<>(questResource, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<QuestResource>> getAllQuests() {
        var getAllProfilesQuery = new GetAllQuestsQuery();
        var quests = questQueryService.handle(getAllProfilesQuery);
        var questResources = quests.stream()
                .map(QuestResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(questResources);
    }

    @GetMapping("/{questId}")
    public ResponseEntity<QuestResource> getQuestById(@PathVariable Long questId) {
        var getQuestByIdQuery = new GetQuestByIdQuery(questId);
        var quest = questQueryService.handle(getQuestByIdQuery);
        if (quest.isPresent()) {
            var questResource = QuestResourceFromEntityAssembler.toResourceFromEntity(quest.get());
            return ResponseEntity.ok(questResource);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{questId}")
    public ResponseEntity<QuestResource> updateQuest(@PathVariable Long questId, @Valid @RequestBody QuestResource resource) {
        var updateQuestCommand = UpdateQuestCommandFromResourceAssembler.toCommandFromResource(questId, resource);
        var quest = questCommandService.handle(updateQuestCommand);
        if (quest.isPresent()) {
            var questResource = QuestResourceFromEntityAssembler.toResourceFromEntity(quest.get());
            return ResponseEntity.ok(questResource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{questId}")
    public ResponseEntity<MessageResource> deleteQuest(@PathVariable Long questId) {
        questCommandService.handle(new DeleteQuestCommand(questId));
        return ResponseEntity.ok(new MessageResource("Quest with id " + questId + " deleted successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<List<QuestResource>> searchQuests(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) QuestType questType,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Theme type) {
        var query = new SearchQuestQuery(title, category, questType, age, type);
        var quests = questQueryService.handle(query);
        var resources = quests.stream()
                .map(QuestResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
