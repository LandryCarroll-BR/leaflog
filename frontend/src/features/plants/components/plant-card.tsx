import { Heading, Subheading } from '@/components/catalyst/heading';
import { differenceInDays } from 'date-fns';
import { Plant } from '@/types/api';
import { cn } from '@/utils/cn';
import { Button } from '@/components/catalyst/button';
import { useNotifications } from '@/components/ui/notifications';
import { useWaterPlant } from '@/features/plants/api/water-plant';
import { EditPlantModal } from '@/features/plants/components/edit-plant-modal';

export function PlantCard({ plant }: { plant: Plant }) {
  const now = new Date();

  const lastWateredDaysAgo = differenceInDays(
    now,
    new Date(plant.lastWatered.value),
  );

  const lastWatered = `Last watered ${lastWateredDaysAgo} day${lastWateredDaysAgo !== 1 ? 's' : ''} ago`;
  const isReadyForWatering =
    lastWateredDaysAgo >= plant.wateringFrequency.value;

  const waterInDays = plant.wateringFrequency.value - lastWateredDaysAgo;

  const { addNotification } = useNotifications();
  const waterPlantMutation = useWaterPlant({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Plant Watered',
        });
      },
    },
  });

  return (
    <div className="border p-4 rounded-xl shadow flex flex-col gap-2">
      <div className="flex items-center justify-between gap-4">
        <Heading level={2} className="!text-lg">
          {plant.name.value}
        </Heading>
        {isReadyForWatering && (
          <Button
            onClick={() =>
              waterPlantMutation.mutate({ data: { id: plant.id.value } })
            }
            color="emerald"
            className="ml-auto"
          >
            Water Now
          </Button>
        )}
        <EditPlantModal plant={plant}>Edit</EditPlantModal>
      </div>
      <div>
        <Subheading className="!text-zinc-500">
          Species: {plant.species.value}
        </Subheading>
        <Subheading className="!text-zinc-500">{lastWatered}</Subheading>
        <Subheading className="!text-zinc-500">
          Watering Frequency: Every {plant.wateringFrequency.value} days
        </Subheading>
      </div>
      <div className="flex items-center gap-4">
        <div
          className={cn(
            'w-fit shrink-0 font-semibold',
            isReadyForWatering ? 'text-emerald-600' : 'text-yellow-600',
          )}
        >
          {isReadyForWatering ? (
            <>Ready to Water!</>
          ) : (
            <>Water in {waterInDays} days</>
          )}
        </div>
        <div className="w-full h-2 rounded-lg overflow-hidden bg-zinc-200">
          <div
            className={cn(
              'h-full rounded-lg transition-all duration-300',
              isReadyForWatering ? 'bg-emerald-600' : 'bg-yellow-500',
            )}
            style={{
              width: `${(lastWateredDaysAgo / plant.wateringFrequency.value) * 100}%`,
            }}
          />
        </div>
      </div>
    </div>
  );
}
