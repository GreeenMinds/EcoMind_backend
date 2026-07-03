package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.quests.application.commandservices.FamilyPlanCommandService;
import pe.greenminds.ecomind_backend.quests.application.internal.queryservices.FamilyPlanStateAssembler;
import pe.greenminds.ecomind_backend.quests.application.queryservices.FamilyPlanState;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestMember;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestSession;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.FamilyPlan;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.FamilyPlanItem;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.ActivateFamilyPlanCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateFamilyPlanCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteFamilyPlanCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.FamilyPlanItemCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateFamilyPlanCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabMemberStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabQuestStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.FamilyPlanStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.MemberRole;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityUserRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestMemberRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestSessionRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.FamilyPlanItemRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.FamilyPlanRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestUserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.util.List;

@Service
public class FamilyPlanCommandServiceImpl implements FamilyPlanCommandService {
    private static final List<QuestStatus> ACTIVE_QUEST_USER_STATUSES =
            List.of(QuestStatus.IN_PROGRESS, QuestStatus.READY_TO_COMPLETE);

    private final FamilyPlanRepository familyPlanRepository;
    private final FamilyPlanItemRepository familyPlanItemRepository;
    private final FamilyUserRepository familyUserRepository;
    private final QuestRepository questRepository;
    private final ActivityRepository activityRepository;
    private final CollabQuestSessionRepository collabQuestSessionRepository;
    private final CollabQuestMemberRepository collabQuestMemberRepository;
    private final QuestUserRepository questUserRepository;
    private final ActivityUserRepository activityUserRepository;
    private final FamilyPlanStateAssembler familyPlanStateAssembler;

    public FamilyPlanCommandServiceImpl(
            FamilyPlanRepository familyPlanRepository,
            FamilyPlanItemRepository familyPlanItemRepository,
            FamilyUserRepository familyUserRepository,
            QuestRepository questRepository,
            ActivityRepository activityRepository,
            CollabQuestSessionRepository collabQuestSessionRepository,
            CollabQuestMemberRepository collabQuestMemberRepository,
            QuestUserRepository questUserRepository,
            ActivityUserRepository activityUserRepository,
            FamilyPlanStateAssembler familyPlanStateAssembler
    ) {
        this.familyPlanRepository = familyPlanRepository;
        this.familyPlanItemRepository = familyPlanItemRepository;
        this.familyUserRepository = familyUserRepository;
        this.questRepository = questRepository;
        this.activityRepository = activityRepository;
        this.collabQuestSessionRepository = collabQuestSessionRepository;
        this.collabQuestMemberRepository = collabQuestMemberRepository;
        this.questUserRepository = questUserRepository;
        this.activityUserRepository = activityUserRepository;
        this.familyPlanStateAssembler = familyPlanStateAssembler;
    }

    @Transactional
    @Override
    public Result<FamilyPlanState, ApplicationError> handle(CreateFamilyPlanCommand command) {
        var validation = validateFamilyPlanInput(
                command.familyId(),
                command.ownerUserId(),
                command.items()
        );
        if (validation != null) {
            return Result.failure(validation);
        }

        var plan = familyPlanRepository.save(
                new FamilyPlan(command.familyId(), command.ownerUserId())
        );
        command.items().forEach(item -> familyPlanItemRepository.save(
                new FamilyPlanItem(plan.getId(), item.questId(), item.startDate())
        ));

        return Result.success(familyPlanStateAssembler.toState(plan));
    }

    @Transactional
    @Override
    public Result<FamilyPlanState, ApplicationError> handle(UpdateFamilyPlanCommand command) {
        var plan = familyPlanRepository.findById(command.familyPlanId());
        if (plan.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "FamilyPlan",
                    command.familyPlanId().toString()
            ));
        }
        if (plan.get().getStatus() != FamilyPlanStatus.DRAFT) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "Only draft family plans can be edited",
                    "FamilyPlan %d is %s".formatted(command.familyPlanId(), plan.get().getStatus())
            ));
        }

        var validation = validateFamilyPlanInput(
                plan.get().getFamilyId(),
                plan.get().getOwnerUserId(),
                command.items()
        );
        if (validation != null) {
            return Result.failure(validation);
        }

        familyPlanItemRepository.deleteByFamilyPlanId(plan.get().getId());
        command.items().forEach(item -> familyPlanItemRepository.save(
                new FamilyPlanItem(plan.get().getId(), item.questId(), item.startDate())
        ));

        return Result.success(familyPlanStateAssembler.toState(plan.get()));
    }

    @Transactional
    @Override
    public Result<FamilyPlanState, ApplicationError> handle(ActivateFamilyPlanCommand command) {
        var plan = familyPlanRepository.findById(command.familyPlanId());
        if (plan.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "FamilyPlan",
                    command.familyPlanId().toString()
            ));
        }
        if (plan.get().getStatus() != FamilyPlanStatus.DRAFT) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "Only draft family plans can be activated",
                    "FamilyPlan %d is %s".formatted(command.familyPlanId(), plan.get().getStatus())
            ));
        }

        var items = familyPlanItemRepository.findByFamilyPlanId(command.familyPlanId());
        if (items.isEmpty()) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "Family plan must have at least one item",
                    "FamilyPlan %d has no items".formatted(command.familyPlanId())
            ));
        }

        var familyMembers = familyUserRepository.findByFamilyId(plan.get().getFamilyId());
        if (familyMembers.isEmpty()) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "Family plan requires family members",
                    "Family %d has no members".formatted(plan.get().getFamilyId())
            ));
        }

        for (var item : items) {
            var validation = validateFamilyQuest(item.getQuestId());
            if (validation != null) {
                return Result.failure(validation);
            }
            for (var familyMember : familyMembers) {
                if (questUserRepository.existsByUserIdAndQuestIdAndStatusIn(
                        familyMember.getUserId(),
                        item.getQuestId(),
                        ACTIVE_QUEST_USER_STATUSES
                )) {
                    return Result.failure(ApplicationError.conflict(
                            "QuestUser",
                            "User %d already has quest %d active".formatted(
                                    familyMember.getUserId(),
                                    item.getQuestId()
                            )
                    ));
                }
            }
        }

        for (var item : items) {
            var session = collabQuestSessionRepository.save(
                    new CollabQuestSession(
                            item.getQuestId(),
                            plan.get().getOwnerUserId(),
                            CollabQuestStatus.STARTED,
                            java.time.LocalDate.now(),
                            null
                    )
            );

            for (var familyMember : familyMembers) {
                var role = familyMember.getUserId().equals(plan.get().getOwnerUserId())
                        ? MemberRole.OWNER
                        : MemberRole.PARTICIPANT;
                collabQuestMemberRepository.save(
                        new CollabQuestMember(
                                session.getId(),
                                familyMember.getUserId(),
                                plan.get().getOwnerUserId(),
                                role,
                                CollabMemberStatus.ACCEPTED
                        )
                );

                var questUser = questUserRepository.save(
                        new QuestUser(familyMember.getUserId(), item.getQuestId(), session.getId())
                );
                activityRepository.findByQuestsIdOrderByOrderAsc(item.getQuestId())
                        .forEach(activity -> activityUserRepository.save(
                                new ActivityUser(
                                        questUser.getId(),
                                        activity.getId(),
                                        activity.getDescription(),
                                        activity.getActivityConfiguration(),
                                        session.getId()
                                )
                        ));
            }

            item.attachCollaborativeSession(session.getId());
            familyPlanItemRepository.save(item);
        }

        plan.get().activate();
        var savedPlan = familyPlanRepository.save(plan.get());
        return Result.success(familyPlanStateAssembler.toState(savedPlan));
    }

    @Transactional
    @Override
    public Result<FamilyPlanState, ApplicationError> handle(DeleteFamilyPlanCommand command) {
        var plan = familyPlanRepository.findById(command.familyPlanId());
        if (plan.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "FamilyPlan",
                    command.familyPlanId().toString()
            ));
        }

        if (plan.get().getStatus() == FamilyPlanStatus.DRAFT) {
            familyPlanItemRepository.deleteByFamilyPlanId(plan.get().getId());
            familyPlanRepository.deleteById(plan.get().getId());
            return Result.success(familyPlanStateAssembler.toState(plan.get()));
        }

        if (plan.get().getStatus() != FamilyPlanStatus.ACTIVE) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "Only draft or active family plans can be deleted",
                    "FamilyPlan %d is %s".formatted(command.familyPlanId(), plan.get().getStatus())
            ));
        }

        familyPlanItemRepository.findByFamilyPlanId(plan.get().getId())
                .stream()
                .map(FamilyPlanItem::getCollaborativeSessionId)
                .filter(java.util.Objects::nonNull)
                .map(collabQuestSessionRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .filter(session -> session.getStatus() == CollabQuestStatus.STARTED)
                .forEach(session -> {
                    session.cancel();
                    collabQuestSessionRepository.save(session);
                });

        plan.get().cancel();
        var savedPlan = familyPlanRepository.save(plan.get());
        return Result.success(familyPlanStateAssembler.toState(savedPlan));
    }

    private ApplicationError validateFamilyPlanInput(
            Long familyId,
            Long ownerUserId,
            List<FamilyPlanItemCommand> items
    ) {
        if (items == null || items.isEmpty()) {
            return ApplicationError.businessRuleViolation(
                    "Family plan must have at least one item",
                    "items is empty"
            );
        }

        var ownerMembership = familyUserRepository.findByFamilyId(familyId)
                .stream()
                .filter(member -> member.getUserId().equals(ownerUserId))
                .findFirst();
        if (ownerMembership.isEmpty()) {
            return ApplicationError.businessRuleViolation(
                    "Owner must belong to the family",
                    "User %d is not in family %d".formatted(ownerUserId, familyId)
            );
        }
        if (ownerMembership.get().getFamilyRole() != FamilyRole.PARENT) {
            return ApplicationError.businessRuleViolation(
                    "Family plan owner must be a parent",
                    "User %d is %s".formatted(ownerUserId, ownerMembership.get().getFamilyRole())
            );
        }

        for (var item : items) {
            var validation = validateFamilyQuest(item.questId());
            if (validation != null) {
                return validation;
            }
        }
        return null;
    }

    private ApplicationError validateFamilyQuest(Long questId) {
        var quest = questRepository.findById(questId);
        if (quest.isEmpty()) {
            return ApplicationError.notFound("Quest", questId.toString());
        }
        if (quest.get().getType() != QuestType.FAMILY) {
            return ApplicationError.businessRuleViolation(
                    "Family plans only accept family quests",
                    "Quest %d is %s".formatted(questId, quest.get().getType())
            );
        }
        if (activityRepository.countByQuestId(questId) < 1) {
            return ApplicationError.businessRuleViolation(
                    "Family quest must have at least one activity",
                    "Quest %d has no activities".formatted(questId)
            );
        }
        return null;
    }
}
