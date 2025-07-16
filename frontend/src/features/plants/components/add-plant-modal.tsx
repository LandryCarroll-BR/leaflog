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
  addPlantInputSchema,
  useAddPlant,
} from '@/features/plants/api/add-plant';
import { useState } from 'react';

export function AddPlantModal({ children }: { children?: React.ReactNode }) {
  const [isOpen, setIsOpen] = useState(false);
  const { addNotification } = useNotifications();
  const addPlantMutation = useAddPlant({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Plant Added',
        });
        setIsOpen(false);
      },
    },
  });

  return (
    <>
      <Button onClick={() => setIsOpen(true)}>{children}</Button>
      <Dialog open={isOpen} onClose={setIsOpen} title="Add Plant">
        <DialogTitle>Add a New Plant</DialogTitle>
        <DialogBody>
          <Form
            id="add-plant"
            onSubmit={(values) => {
              addPlantMutation.mutate({ data: values });
            }}
            schema={addPlantInputSchema}
          >
            {({ register, formState }) => (
              <>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                  <Input
                    label="Name"
                    error={formState.errors['name']}
                    registration={register('name')}
                  />

                  <Input
                    label="Species"
                    error={formState.errors['species']}
                    registration={register('species')}
                  />
                </div>

                <Input
                  label="Watering Frequency"
                  type="number"
                  error={formState.errors['wateringFrequencyInDays']}
                  registration={register('wateringFrequencyInDays')}
                />

                <Input
                  label="Date Last Watered"
                  type="date"
                  error={formState.errors['lastWatered']}
                  registration={register('lastWatered')}
                />

                <Textarea
                  label="Notes"
                  error={formState.errors['notes']}
                  registration={register('notes')}
                />
                <DialogActions>
                  <Button outline onClick={() => setIsOpen(false)}>
                    Cancel
                  </Button>
                  <Button type="submit">Add Plant</Button>
                </DialogActions>
              </>
            )}
          </Form>
        </DialogBody>
      </Dialog>
    </>
  );
}
