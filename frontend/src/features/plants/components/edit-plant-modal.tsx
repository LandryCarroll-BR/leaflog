import { Button } from '@/components/catalyst/button';
import {
  Dialog,
  DialogActions,
  DialogBody,
  DialogTitle,
} from '@/components/catalyst/dialog';
import { Form, Input, Textarea } from '@/components/ui/form';
import { useNotifications } from '@/components/ui/notifications';
import {
  editPlantInputSchema,
  useEditPlant,
} from '@/features/plants/api/edit-plant';
import { DeletePlantModal } from '@/features/plants/components/delete-plant-modal';
import { Plant } from '@/types/api';
import { useState } from 'react';

export function EditPlantModal({
  plant,
  children,
}: {
  plant: Plant;
  children?: React.ReactNode;
}) {
  const [isOpen, setIsOpen] = useState(false);
  const { addNotification } = useNotifications();
  const editPlantMutation = useEditPlant({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Plant Updated',
        });
        setIsOpen(false);
      },
    },
  });

  return (
    <>
      <Button outline onClick={() => setIsOpen(true)}>
        {children}
      </Button>
      <Dialog open={isOpen} onClose={setIsOpen} title="Edit Plant">
        <DialogTitle>Edit a New Plant</DialogTitle>
        <DialogBody>
          <Form
            id="edit-plant"
            onSubmit={(values) => {
              editPlantMutation.mutate({
                data: values,
              });
            }}
            schema={editPlantInputSchema}
          >
            {({ register, formState }) => (
              <>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                  <input
                    {...register('id')}
                    defaultValue={plant.id.value}
                    hidden
                  />
                  <Input
                    label="Name"
                    error={formState.errors['name']}
                    registration={register('name')}
                    defaultValue={plant.name.value}
                  />

                  <Input
                    label="Species"
                    error={formState.errors['species']}
                    registration={register('species')}
                    defaultValue={plant.species.value}
                  />
                </div>

                <Input
                  label="Watering Frequency"
                  type="number"
                  error={formState.errors['wateringFrequency']}
                  registration={register('wateringFrequency')}
                  defaultValue={plant.wateringFrequency.value.toString()}
                />

                <Input
                  label="Date Last Watered"
                  type="date"
                  error={formState.errors['lastWatered']}
                  registration={register('lastWatered')}
                  defaultValue={
                    new Date(plant.lastWatered.value)
                      .toISOString()
                      .split('T')[0]
                  }
                />

                <Textarea
                  label="Notes"
                  error={formState.errors['notes']}
                  registration={register('notes')}
                  defaultValue={plant.notes?.value || ''}
                />
                <DialogActions>
                  <Button outline onClick={() => setIsOpen(false)}>
                    Cancel
                  </Button>
                  <Button type="submit">Save</Button>
                </DialogActions>
              </>
            )}
          </Form>
          <div className="mr-auto -mt-9">
            <DeletePlantModal plant={plant}>Delete</DeletePlantModal>
          </div>
        </DialogBody>
      </Dialog>
    </>
  );
}
