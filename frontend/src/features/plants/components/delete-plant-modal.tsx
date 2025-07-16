import { Button } from '@/components/catalyst/button';
import {
  Alert,
  AlertActions,
  AlertBody,
  AlertDescription,
  AlertTitle,
} from '@/components/catalyst/alert';
import { Form } from '@/components/ui/form';
import { useNotifications } from '@/components/ui/notifications';
import {
  deletePlantInputSchema,
  useDeletePlant,
} from '@/features/plants/api/delete-plant';
import { Plant } from '@/types/api';
import { useState } from 'react';

export function DeletePlantModal({
  plant,
  children,
}: {
  plant: Plant;
  children?: React.ReactNode;
}) {
  const [isOpen, setIsOpen] = useState(false);
  const { addNotification } = useNotifications();
  const deletePlantMutation = useDeletePlant({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Plant Deleted',
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
      <Alert open={isOpen} onClose={setIsOpen} title="Delete Plant">
        <AlertTitle>
          Are you sure you want to delete {plant.plantName.value}?
        </AlertTitle>
        <AlertDescription>
          This action cannot be undone. All data associated with this plant will
          be permanently deleted.
        </AlertDescription>
        <AlertBody>
          <Form
            id="delete-plant"
            onSubmit={(values) => {
              deletePlantMutation.mutate({
                data: values,
              });
            }}
            schema={deletePlantInputSchema}
          >
            {({ register }) => (
              <>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                  <input {...register('id')} defaultValue={plant.id} hidden />
                </div>

                <AlertActions>
                  <Button outline onClick={() => setIsOpen(false)}>
                    Cancel
                  </Button>
                  <Button color="red" className="ml-auto" type="submit">
                    Delete Forever
                  </Button>
                </AlertActions>
              </>
            )}
          </Form>
        </AlertBody>
      </Alert>
    </>
  );
}
