package pe.greenminds.ecomind_backend.learning.application.internal.eventhandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;
import pe.greenminds.ecomind_backend.learning.domain.repositories.EducationalMaterialRepository;

@Component
public class LearningSeedApplicationReadyEventHandler {

    private final EducationalMaterialRepository repository;
    private final boolean enabled;

    public LearningSeedApplicationReadyEventHandler(
            EducationalMaterialRepository repository,
            @Value("${learning.seed.enabled}") boolean enabled
    ) {
        this.repository = repository;
        this.enabled = enabled;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        if (!enabled) return;
        if (repository.findAll().isEmpty()) {
            seed();
        }
    }

    private void seed() {
        repository.save(new EducationalMaterial(
                "♻️ Recycling Basics",
                "Learn the fundamentals of recycling and how to properly sort your waste.",
                "Recycling is the process of converting waste materials into new materials and objects. This reduces the consumption of fresh raw materials, energy usage, air pollution, and water pollution.\n\n## Why Recycle?\n- Conserves natural resources\n- Saves energy\n- Reduces landfill waste\n- Creates jobs\n\n## What Can Be Recycled?\n- Paper and cardboard\n- Glass bottles and jars\n- Metal cans and foil\n- Plastic bottles and containers\n\n## Tips for Better Recycling\n1. Rinse containers before recycling\n2. Keep recyclables loose (not bagged)\n3. Check local recycling guidelines",
                MaterialType.TEXT,
                MaterialCategory.RECYCLE,
                "https://images.unsplash.com/photo-1532996122724-e3c354a0b15b?w=400",
                5
        ));

        repository.save(new EducationalMaterial(
                "💧 Water Conservation at Home",
                "Simple ways to reduce water usage in your daily life.",
                "Water is one of our most precious resources. With climate change affecting water availability, conserving water at home has never been more important.\n\n## Why Conserve Water?\n- Protect natural ecosystems\n- Reduce energy used for water treatment\n- Save money on utility bills\n- Ensure water availability for future generations\n\n## Easy Ways to Save Water\n- Fix leaky faucets promptly\n- Take shorter showers\n- Turn off tap while brushing teeth\n- Use a broom instead of a hose for cleaning driveways\n- Collect rainwater for plants\n\n## Did You Know?\nA single dripping faucet can waste over 3,000 liters of water per year!",
                MaterialType.TEXT,
                MaterialCategory.WATER,
                "https://images.unsplash.com/photo-1581244277943-fe4a8c5f1c12?w=400",
                4
        ));

        repository.save(new EducationalMaterial(
                "🔋 Understanding Renewable Energy",
                "An overview of solar, wind, and other clean energy sources.",
                "Renewable energy comes from natural sources that are constantly replenished. Unlike fossil fuels, renewable energy produces little to no greenhouse gas emissions.\n\n## Types of Renewable Energy\n\n### Solar Energy\nConverts sunlight into electricity using photovoltaic panels. Perfect for homes with roof space.\n\n### Wind Energy\nUses wind turbines to generate power. One of the fastest-growing energy sources.\n\n### Hydropower\nGenerates electricity from flowing water. The most established renewable source.\n\n### Geothermal\nUses heat from beneath the Earth's surface for heating and electricity.\n\n## Benefits\n- Reduces carbon emissions\n- Creates energy independence\n- Lower long-term costs\n- Sustainable and abundant",
                MaterialType.TEXT,
                MaterialCategory.ENERGY,
                "https://images.unsplash.com/photo-1509391366360-2e959784a276?w=400",
                6
        ));

        repository.save(new EducationalMaterial(
                "🌍 Climate Change Explained",
                "Understanding the causes and effects of global climate change.",
                "Climate change refers to long-term shifts in temperatures and weather patterns. While natural factors play a role, human activities have been the main driver since the 1800s.\n\n## Main Causes\n- Burning fossil fuels (coal, oil, gas)\n- Deforestation\n- Industrial agriculture\n- Waste decomposition\n\n## Effects We're Already Seeing\n- Rising global temperatures\n- More frequent extreme weather events\n- Melting ice caps and rising sea levels\n- Disruption of ecosystems\n\n## What You Can Do\n- Reduce energy consumption\n- Use public transport or bike\n- Eat less meat\n- Support renewable energy\n- Plant trees\n\nEvery action, no matter how small, contributes to the solution!",
                MaterialType.TEXT,
                MaterialCategory.ENVIRONMENT,
                "https://images.unsplash.com/photo-1611273426858-450d8e3c9fce?w=400",
                7
        ));

        repository.save(new EducationalMaterial(
                "🗑️ Composting 101",
                "Turn your kitchen waste into nutrient-rich soil for your garden.",
                "Composting is nature's way of recycling organic waste into valuable fertilizer. It's easy to start and makes a huge difference for the environment.\n\n## What Can You Compost?\n- Fruit and vegetable scraps\n- Coffee grounds and filters\n- Eggshells\n- Grass clippings and leaves\n- Shredded paper\n\n## What NOT to Compost\n- Meat and dairy products\n- Oily foods\n- Diseased plants\n- Pet waste\n\n## Getting Started\n1. Choose a compost bin or pile location\n2. Layer green (nitrogen-rich) and brown (carbon-rich) materials\n3. Keep it moist but not wet\n4. Turn regularly for aeration\n5. Wait 2-6 months for finished compost\n\nYour garden will thank you!",
                MaterialType.TEXT,
                MaterialCategory.RECYCLE,
                "https://images.unsplash.com/photo-1585320806297-9794b3e4eeae?w=400",
                5
        ));

        repository.save(new EducationalMaterial(
                "🚰 How Water Treatment Works",
                "Infographic showing the journey of water from source to your tap.",
                "Water treatment is a fascinating process that ensures the water coming out of your tap is safe to drink. This infographic walks through each step of the journey.\n\nStep 1: Collection - Water is collected from rivers, lakes, or reservoirs.\nStep 2: Screening - Large debris like sticks and leaves are removed.\nStep 3: Coagulation - Chemicals are added to make small particles clump together.\nStep 4: Sedimentation - The clumps settle at the bottom.\nStep 5: Filtration - Water passes through sand and charcoal filters.\nStep 6: Disinfection - Chlorine or UV light kills remaining microorganisms.\nStep 7: Distribution - Clean water travels through pipes to your home.\n\nFun fact: A water treatment plant can process millions of liters per day!",
                MaterialType.INFOGRAPHIC,
                MaterialCategory.WATER,
                "https://images.unsplash.com/photo-1538300342682-cf57afb97285?w=400",
                3
        ));

        repository.save(new EducationalMaterial(
                "🏠 Energy Efficiency Tips",
                "Reduce your energy bills and carbon footprint with these practical tips.",
                "Making your home more energy efficient is one of the best ways to save money and help the environment.\n\n## Quick Wins\n- Switch to LED light bulbs (use 75% less energy)\n- Unplug electronics when not in use\n- Use a programmable thermostat\n- Seal drafts around windows and doors\n\n## Medium Investments\n- Add insulation to attic and walls\n- Install energy-efficient windows\n- Upgrade to ENERGY STAR appliances\n\n## Long-term Solutions\n- Install solar panels\n- Switch to a heat pump\n- Consider a green roof\n\n## Savings Calculator\nA typical home can save 25-30% on energy bills by implementing these measures. That's hundreds of dollars per year!",
                MaterialType.TEXT,
                MaterialCategory.ENERGY,
                "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=400",
                4
        ));

        repository.save(new EducationalMaterial(
                "🌿 Biodiversity and You",
                "Why biodiversity matters and how to protect it in your community.",
                "Biodiversity - the variety of life on Earth - is essential for the health of our planet and our own well-being.\n\n## Why Biodiversity Matters\n- Provides food, clean water, and medicine\n- Supports pollination of crops\n- Regulates climate\n- Protects against natural disasters\n\n## Threats to Biodiversity\n- Habitat destruction\n- Pollution\n- Invasive species\n- Climate change\n- Overexploitation\n\n## How You Can Help\n- Plant native species in your garden\n- Avoid pesticides\n- Support local conservation efforts\n- Reduce your carbon footprint\n- Educate others about biodiversity\n\nRemember: When we save nature, we save ourselves.",
                MaterialType.TEXT,
                MaterialCategory.ENVIRONMENT,
                "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=400",
                6
        ));

        repository.save(new EducationalMaterial(
                "♻️ Plastic Pollution Crisis",
                "Video explaining the impact of plastic pollution and solutions.",
                "Plastic pollution is one of the most pressing environmental issues of our time. Each year, 8 million tons of plastic enter our oceans.\n\n## The Problem\n- Only 9% of plastic ever produced has been recycled\n- 12 million tons of plastic enter oceans annually\n- By 2050, there could be more plastic than fish in the sea\n- Microplastics have been found in drinking water, food, and even human blood\n\n## Solutions\n1. Reduce single-use plastics\n2. Choose reusable alternatives\n3. Support legislation to ban unnecessary plastics\n4. Participate in clean-up events\n5. Advocate for extended producer responsibility\n\n## What You Can Do Today\nStart by carrying a reusable water bottle, shopping bag, and coffee cup. These small changes add up!",
                MaterialType.VIDEO,
                MaterialCategory.RECYCLE,
                "https://images.unsplash.com/photo-1618477461853-cf6ed80faba5?w=400",
                8
        ));

        repository.save(new EducationalMaterial(
                "💡 Green Energy Revolution",
                "Video showcasing innovations in renewable energy technology.",
                "The green energy revolution is transforming how we power our world. From floating solar farms to next-generation wind turbines, innovation is accelerating.\n\n## Breakthrough Technologies\n- Perovskite solar cells (cheaper, more efficient)\n- Floating wind turbines (access deeper waters)\n- Green hydrogen (clean fuel for industry)\n- Virtual power plants (networked home batteries)\n\n## Global Progress\n- Renewable energy now cheaper than coal in most places\n- Over 30% of global electricity comes from renewables\n- Solar energy is growing faster than any other source\n- Many countries targeting 100% renewable by 2050\n\n## The Future is Bright\nThe transition to renewable energy is not just necessary - it's already happening. And you can be part of it.",
                MaterialType.VIDEO,
                MaterialCategory.ENERGY,
                "https://images.unsplash.com/photo-1466611653911-95081537e5b7?w=400",
                10
        ));
    }
}
